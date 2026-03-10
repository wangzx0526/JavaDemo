package com.zane.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zane.ISysRoleService;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.RoleQueryDto;
import com.zane.entity.SysRole;
import com.zane.entity.SysRoleMenu;
import com.zane.entity.SysUserRole;
import com.zane.mapper.SysRoleMapper;
import com.zane.mapper.SysRoleMenuMapper;
import com.zane.mapper.SysUserRoleMapper;
import com.zane.vo.SysRoleTreeVo;
import com.zane.vo.SysRoleVo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    private final SysRoleMapper roleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysRoleVo> getRoleList() {
        List<SysRole> roleList = roleMapper.selectList(new LambdaQueryWrapper<>(SysRole.class));
        List<SysRoleVo> result = BeanUtil.copyToList(roleList, SysRoleVo.class);
        for (SysRoleVo vo : result) {
            vo.setMenuIds(roleMapper.getMenuIdsByRoleId(vo.getId()));
        }
        return result;
    }

    @Override
    public TableDataInfo<SysRoleVo> getRolePageList(Page page, RoleQueryDto roleQueryDto) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleQueryDto.getRoleName()), SysRole::getRoleName, roleQueryDto.getRoleName())
                .like(StringUtils.isNotBlank(roleQueryDto.getRoleCode()), SysRole::getRoleCode, roleQueryDto.getRoleCode())
                .eq(roleQueryDto.getStatus() != null, SysRole::getStatus, roleQueryDto.getStatus())
                .orderByAsc(SysRole::getSort);

        if (roleQueryDto.getStartTime() != null || roleQueryDto.getEndTime() != null) {
            LocalDateTime begin = roleQueryDto.getStartTime() != null
                    ? roleQueryDto.getStartTime().atStartOfDay()
                    : null;
            LocalDateTime end = roleQueryDto.getEndTime() != null
                    ? roleQueryDto.getEndTime().atTime(23, 59, 59)
                    : null;
            if (begin != null && end != null) {
                queryWrapper.between(SysRole::getCreateTime, begin, end);
            } else if (begin != null) {
                queryWrapper.ge(SysRole::getCreateTime, begin);
            } else {
                queryWrapper.le(SysRole::getCreateTime, end);
            }
        }

        IPage<SysRole> resultPage = roleMapper.selectPage(page, queryWrapper);
        IPage<SysRoleVo> voPage = resultPage.convert(role -> {
            SysRoleVo vo = new SysRoleVo();
            BeanUtil.copyProperties(role, vo);
            vo.setMenuIds(roleMapper.getMenuIdsByRoleId(role.getId()));
            return vo;
        });

        return TableDataInfo.build(voPage);

    }

    @Override
    public SysRoleVo getRoleById(Long id) {
        if (id == null) {
            return null;
        }
        SysRole role = roleMapper.selectById(id);
        if (role == null) {
            return null;
        }
        SysRoleVo roleVo = BeanUtil.toBean(role, SysRoleVo.class);
        roleVo.setMenuIds(roleMapper.getMenuIdsByRoleId(id));
        return roleVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByRole(SysRole role) {
        if (role == null || role.getId() == null) {
            return false;
        }
        if (roleMapper.selectById(role.getId()) == null) {
            return false;
        }
        if (roleMapper.updateById(role) > 0) {
            sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, role.getId()));
            if (role.getMenuIds() != null && !role.getMenuIds().isEmpty()) {
                for (Long menuId : role.getMenuIds()) {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(role.getId());
                    sysRoleMenu.setMenuId(menuId);
                    sysRoleMenuMapper.insert(sysRoleMenu);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }
        if (roleMapper.selectById(id) == null) {
            return false;
        }
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
        return roleMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getRoleId, ids));
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, ids));
        return roleMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(SysRole role) {
        if (role == null) {
            return false;
        }
        role.setId(null);
        if (roleMapper.insert(role) > 0) {
            if (role.getMenuIds() != null && !role.getMenuIds().isEmpty()) {
                for (Long menuId : role.getMenuIds()) {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(role.getId());
                    sysRoleMenu.setMenuId(menuId);
                    sysRoleMenuMapper.insert(sysRoleMenu);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<SysRoleTreeVo> getRoleTree() {
        List<SysRole> roleList = roleMapper.selectList(new LambdaQueryWrapper<>(SysRole.class));
        List<SysRoleTreeVo> result = BeanUtil.copyToList(roleList, SysRoleTreeVo.class);
        return result;
    }

}
