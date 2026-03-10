package com.zane.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zane.ISysUserService;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.QueryUserDto;
import com.zane.dto.UserCreateDto;
import com.zane.dto.UserUpdateDto;
import com.zane.entity.SysUser;
import com.zane.entity.SysUserRole;
import com.zane.mapper.SysRoleMapper;
import com.zane.mapper.SysUserMapper;
import com.zane.mapper.SysUserRoleMapper;
import com.zane.vo.SysUserVo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class SysUserServiceImp implements ISysUserService {

    private final SysUserMapper userMapper;
    private final SysRoleMapper roleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserVo> getList() {
        List<SysUser> userList = userMapper.selectList(new LambdaQueryWrapper<>(SysUser.class));
        List<SysUserVo> result = BeanUtil.copyToList(userList, SysUserVo.class);
        return result;
    }

    @Override
    public TableDataInfo<SysUserVo> getPageList(Page page, QueryUserDto queryUserDto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(queryUserDto.getUserName()), SysUser::getUserName, queryUserDto.getUserName())
                .like(StringUtils.isNotBlank(queryUserDto.getPhone()), SysUser::getPhone, queryUserDto.getPhone())
                .eq(queryUserDto.getStatus() != null, SysUser::getStatus, queryUserDto.getStatus());

        IPage<SysUser> resultPage = userMapper.selectPage(page, queryWrapper);

        IPage<SysUserVo> voPage = resultPage.convert(user -> {
            SysUserVo vo = new SysUserVo();
            BeanUtil.copyProperties(user, vo);
            return vo;
        });

        return TableDataInfo.build(voPage);
    }

    @Override
    public SysUserVo getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        SysUser user = userMapper.selectById(id);
        if (ObjectUtil.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }

        SysUserVo vo = BeanUtil.toBean(user, SysUserVo.class);

        List<Long> roleIds = roleMapper.getRoleIdsByUserId(id);
        vo.setRoleIds(roleIds);

        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateUser(UserUpdateDto user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        SysUser exist = userMapper.selectById(user.getId());
        if (ObjectUtil.isNull(exist)) {
            throw new IllegalArgumentException("用户不存在");
        }
        SysUser userEntity = BeanUtil.toBean(user, SysUser.class);
        if (StringUtils.isNotBlank(user.getPassWord())) {
            userEntity.setPassWord(BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt()));
        } else {
            userEntity.setPassWord(exist.getPassWord());
        }
        userMapper.updateById(userEntity);
        
        // 只有在传入了角色ID列表时才更新角色关联
        if (user.getRoleIds() != null) {
            sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userEntity.getId()));
            if (CollectionUtils.isNotEmpty(user.getRoleIds())) {
                user.getRoleIds().forEach(roleId -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setUserId(userEntity.getId());
                    sysUserRole.setRoleId(roleId);
                    sysUserRoleMapper.insert(sysUserRole);
                });
            }
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteUser(Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        if (ObjectUtil.isNull(userMapper.selectById(id))) {
            return false;
        }
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
        return userMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean batchDeleteUser(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, ids));
        int deleted = userMapper.deleteBatchIds(ids);
        return deleted > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean insertUser(UserCreateDto user) {
        if (user == null || StringUtils.isBlank(user.getUserName())) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        long count = userMapper.selectCount(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, user.getUserName().trim()));
        if (count > 0) {
            throw new IllegalArgumentException("用户名已存在");
        }
        SysUser userEntity = BeanUtil.toBean(user, SysUser.class);
        if (StringUtils.isNotBlank(user.getPassWord())) {
            userEntity.setPassWord(BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt()));
        }
        userMapper.insert(userEntity);
        if (CollectionUtils.isNotEmpty(user.getRoleIds())) {
            user.getRoleIds().forEach(roleId -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userEntity.getId());
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            });
        }
        return true;
    }
}
