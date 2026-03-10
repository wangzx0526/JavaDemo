package com.zane.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zane.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 用户Mapper接口
 *
 * @author Zane
 * @since 2026-02-12
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    Set<String> getMenuPermission(Long userId);

    Set<String> getRoleCodes(Long userId);
}