package com.zane.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zane.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<Long> getRoleIdsByUserId(Long userId);

    List<Long> getMenuIdsByRoleId(@Param("roleId") Long roleId);
}
