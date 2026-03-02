package com.zane;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.MenuQueryDto;
import com.zane.entity.SysMenu;
import com.zane.vo.SysMenuTreeVo;
import com.zane.vo.SysMenuVo;

import java.util.List;

/**
 * 菜单权限 Service（RBAC 菜单管理）
 *
 * @author wzx
 */
public interface ISysMenuService {

    /** 菜单列表（扁平） */
    List<SysMenuVo> getList(String menuName, Integer status);

    /** 菜单树（树形，用于管理页与下拉） */
    List<SysMenuTreeVo> getMenuTree();

    /** 根据 ID 查询 */
    SysMenuVo getMenuById(Long id);

    /** 新增菜单 */
    boolean addMenu(SysMenu menu);

    /** 修改菜单 */
    boolean updateMenu(SysMenu menu);

    /** 删除菜单（存在子菜单时不允许删除） */
    boolean deleteById(Long id);
}