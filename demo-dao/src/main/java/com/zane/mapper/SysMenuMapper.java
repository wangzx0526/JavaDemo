package com.zane.mapper;

import com.zane.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wzx
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2026-02-17 00:47:40
* @Entity com.zane.entity.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户ID查询菜单列表
     */
    List<SysMenu> selectMenusByUserId(@Param("userId") Long userId);
}




