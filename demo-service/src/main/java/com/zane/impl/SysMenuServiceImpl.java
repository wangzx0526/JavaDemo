package com.zane.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zane.ISysMenuService;
import com.zane.entity.SysMenu;
import com.zane.mapper.SysMenuMapper;
import com.zane.vo.SysMenuTreeVo;
import com.zane.vo.SysMenuVo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements ISysMenuService {

    private static final String BUTTON_MENU_TYPE = "F";

    private final SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuVo> getList(String menuName, Integer status) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(menuName), SysMenu::getMenuName, menuName)
                .eq(status != null, SysMenu::getStatus, status)
                .orderByAsc(SysMenu::getSort);
        return BeanUtil.copyToList(sysMenuMapper.selectList(queryWrapper), SysMenuVo.class);
    }

    @Override
    public List<SysMenuTreeVo> getMenuTree() {
        List<SysMenu> list = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        return buildTree(BeanUtil.copyToList(list, SysMenuTreeVo.class), 0L);
    }

    @Override
    public SysMenuVo getMenuById(Long id) {
        if (id == null) {
            return null;
        }
        return BeanUtil.toBean(sysMenuMapper.selectById(id), SysMenuVo.class);
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

    @Override
    public List<SysMenuTreeVo> getMenuTreeByUserId() {
        Long userId = Long.valueOf(StpUtil.getLoginId().toString());
        List<SysMenu> list = sysMenuMapper.selectMenusByUserId(userId).stream()
                .filter(menu -> !BUTTON_MENU_TYPE.equalsIgnoreCase(menu.getMenuType()))
                .toList();
        return buildTree(BeanUtil.copyToList(list, SysMenuTreeVo.class), 0L);
    }

    @Override
    public List<String> getPermsByUserId(Long userId) {
        if (userId == null) {
            return List.of();
        }
        return sysMenuMapper.selectPermsByUserId(userId).stream()
                .filter(StringUtils::isNotBlank)
                .flatMap(perms -> Arrays.stream(perms.split(",")))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .distinct()
                .toList();
    }

    @Override
    public List<String> getLoginUserPerms() {
        if (!StpUtil.isLogin()) {
            return List.of();
        }
        return getPermsByUserId(Long.valueOf(StpUtil.getLoginId().toString()));
    }

    private List<SysMenuTreeVo> buildTree(List<SysMenuTreeVo> nodes, Long parentId) {
        return nodes.stream()
                .filter(node -> parentId.equals(node.getParentId()))
                .peek(node -> node.setChildren(buildTree(nodes, node.getId())))
                .collect(Collectors.toList());
    }
}
