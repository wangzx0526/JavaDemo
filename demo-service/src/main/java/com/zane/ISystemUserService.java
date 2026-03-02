package com.zane;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.QueryUserDto;
import com.zane.dto.UserCreateDto;
import com.zane.dto.UserUpdateDto;
import com.zane.entity.SysUser;
import com.zane.vo.SysUserVo;

import java.util.List;

public interface ISystemUserService {
    /**
     * get user list method
     * @return
     */
//    List<UserVo> getUserList();
    List<SysUserVo> getList();

    /**
     * get user page list method
     * @return
     */
    TableDataInfo<SysUserVo> getPageList(Page page, QueryUserDto queryUserDto);

    SysUserVo getUserById(Long id);

    Boolean updateUser(UserUpdateDto user);

    Boolean deleteUser(Long id);

    Boolean batchDeleteUser(List<Long> ids);

    Boolean insertUser(UserCreateDto user);
}
