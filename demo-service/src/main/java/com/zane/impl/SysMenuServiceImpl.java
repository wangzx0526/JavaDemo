package com.zane.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zane.ISysMenuService;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.MenuQueryDto;
import com.zane.entity.SysMenu;
import com.zane.mapper.SysMenuMapper;
import com.zane.vo.SysMenuTreeVo;
import com.zane.vo.SysMenuVo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限 Service 实现（RBAC 菜单管理）
 *
 * @author wzx
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    private final SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuVo> getList(String menuName, Integer status) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(menuName), SysMenu::getMenuName, menuName)
                .eq(status != null, SysMenu::getStatus, status)
                .orderByAsc(SysMenu::getSort);

        List<SysMenu> list = sysMenuMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(list, SysMenuVo.class);
    }


    @Override
    public List<SysMenuTreeVo> getMenuTree() {
        List<SysMenu> list = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<SysMenuTreeVo> nodes = BeanUtil.copyToList(list, SysMenuTreeVo.class);
        return buildTree(nodes, 0L);
    }

    /** 递归构建树，parentId 为 0 表示顶级 */
    private List<SysMenuTreeVo> buildTree(List<SysMenuTreeVo> nodes, Long parentId) {
        return nodes.stream()
                .filter(n -> parentId.equals(n.getParentId()))
                .peek(n -> n.setChildren(buildTree(nodes, n.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public SysMenuVo getMenuById(Long id) {
        if (id == null) {
            return null;
        }
        SysMenu menu = sysMenuMapper.selectById(id);
        return BeanUtil.toBean(menu, SysMenuVo.class);
    }

    @Override
    public boolean addMenu(SysMenu menu) {
        return sysMenuMapper.insert(menu) > 0;
    }

    @Override
    public boolean updateMenu(SysMenu menu) {
        return sysMenuMapper.updateById(menu) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }
        long count = sysMenuMapper.selectCount(
                new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (count > 0) {
            log.warn("存在子菜单，无法删除菜单 id={}", id);
            return false;
        }
        return sysMenuMapper.deleteById(id) > 0;
    }
}