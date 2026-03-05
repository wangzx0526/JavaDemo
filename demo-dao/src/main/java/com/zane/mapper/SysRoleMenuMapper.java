package com.zane.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zane.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关联表 Mapper
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}
