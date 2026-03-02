package com.zane.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zane.ISystemLoginService;
import com.zane.dto.UserRegisterDto;
import com.zane.entity.SysUser;
import com.zane.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class SystemLoginServiceImp implements ISystemLoginService {

    private final SysUserMapper userMapper;

    @Override
    public String  login(String username, String password) {
        // 1. checkout verify code

        // 2. decrypt password

        // 3. encrypt password
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getUserName, username);
        SysUser user = userMapper.selectOne(lqw);

        if (Objects.isNull(user)) {
            log.info("用户不存在");
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPassWord())) {
            log.info("密码错误");
            return null;
        }

        // 4. sa-token login
        StpUtil.login(user.getId());

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
        StpUtil.logout();
    }
}
