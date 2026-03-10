package com.zane;

import com.zane.entity.SysMenu;
import com.zane.vo.SysMenuTreeVo;
import com.zane.vo.SysMenuVo;

import java.util.List;

public interface ISysMenuService {

    List<SysMenuVo> getList(String menuName, Integer status);

    List<SysMenuTreeVo> getMenuTree();

    SysMenuVo getMenuById(Long id);

    boolean addMenu(SysMenu menu);

    boolean updateMenu(SysMenu menu);

    boolean deleteById(Long id);

    List<SysMenuTreeVo> getMenuTreeByUserId();

    List<String> getPermsByUserId(Long userId);
}
