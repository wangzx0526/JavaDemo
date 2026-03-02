package com.zane.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zane.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 *
 * @author Zane
 * @since 2026-02-12
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}