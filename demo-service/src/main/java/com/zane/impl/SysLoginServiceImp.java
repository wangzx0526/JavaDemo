package com.zane.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zane.ISysLoginService;
import com.zane.constant.Constants;
import com.zane.constant.UserConstants;
import com.zane.core.enums.DeviceType;
import com.zane.dto.UserRegisterDto;
import com.zane.entity.SysDept;
import com.zane.entity.SysUser;
import com.zane.event.LogininforEvent;
import com.zane.helper.LoginHelper;
import com.zane.mapper.SysDeptMapper;
import com.zane.mapper.SysUserMapper;
import com.zane.utils.MessageUtils;
import com.zane.utils.ServletUtils;
import com.zane.utils.spring.SpringUtils;
import com.zane.vo.LoginInforVo;
import com.zane.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class SysLoginServiceImp implements ISysLoginService {

    private final SysUserMapper userMapper;
    private final SysDeptMapper deptMapper;

    @Override
    public String login(String username, String password) {
        // 1. checkout verify code

        // 2. decrypt password(encrypted by front-end)

        // 3. find the corresponding user
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getUserName, username);
        SysUser user = userMapper.selectOne(lqw);

        if (Objects.isNull(user)) {
            log.info("用户不存在");
            throw new RuntimeException("用户不存在");
        }

        if (!BCrypt.checkpw(password, user.getPassWord())) {
            log.info("密码错误");
            throw new RuntimeException("密码错误");
        }

        LoginInforVo loginUser = buildLoginUser(user);
        // 4. sa-token login
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);
        recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));

        // 5. return token
        return StpUtil.getTokenValue();
    }

    @Override
    public Boolean register(UserRegisterDto user) {
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }

        // encrypt password
        String password = user.getPassWord();
        String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassWord(encodedPassword);
        // 构造 User 类型对象
        SysUser tmp = BeanUtil.copyProperties(user, SysUser.class);

        return userMapper.insertOrUpdate(tmp);
    }

    @Override
    public void logout() {
        try {
            LoginInforVo loginUser = LoginHelper.getLoginUser();
            recordLogininfor(loginUser.getSysUser().getUserName(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
        } catch (NotLoginException ignored) {
        } finally {
            try {
                StpUtil.logout();
            } catch (NotLoginException ignored) {
            }
        }
    }

    @Override
    public LoginInforVo getLoginInforVo() {
        LoginInforVo loginInforVo = new LoginInforVo();
        Long userId = 1L;
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<>(SysUser.class).eq(SysUser::getId, userId));
        if (Objects.nonNull(sysUser)) {
            loginInforVo.setSysUser(BeanUtil.toBean(sysUser, SysUserVo.class));
        }

        SysDept sysDept = deptMapper.selectOne(new LambdaQueryWrapper<>(SysDept.class).eq(SysDept::getId, sysUser.getDeptId()));
        if (Objects.nonNull(sysDept)) {
            loginInforVo.setSysDept(sysDept);
        }

        Set<String> roleCodes = userMapper.getRoleCodes(userId);
        if (Objects.nonNull(roleCodes)) {
            loginInforVo.setRoleCodes(roleCodes);
        }

        if (userId == 1L) {
            loginInforVo.setMenuPermission(Set.of("*:*:*"));
            return loginInforVo;
        }

        Set<String> menuPermission = userMapper.getMenuPermission(userId);
        if (Objects.nonNull(menuPermission)) {
            loginInforVo.setMenuPermission(menuPermission);
        }

        return loginInforVo;
    }


    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    private void recordLogininfor(String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

    /**
     * 构建登录用户
     */
    private LoginInforVo buildLoginUser(SysUser user) {
        LoginInforVo loginUser = new LoginInforVo();
        loginUser.setSysUser(BeanUtil.toBean(user, SysUserVo.class));
        SysDept dept = deptMapper.selectOne(new LambdaQueryWrapper<>(SysDept.class).eq(SysDept::getId, user.getDeptId()));
        loginUser.setSysDept(dept);
        loginUser.setUserType("sys_user");
        loginUser.setRoleCodes(userMapper.getRoleCodes(user.getId()));
        if (user.getId().equals(UserConstants.ADMIN_ID)) {
            loginUser.setMenuPermission(Set.of("*:*:*"));
        } else {
            loginUser.setMenuPermission(userMapper.getMenuPermission(user.getId()));
        }
        return loginUser;
    }
}
