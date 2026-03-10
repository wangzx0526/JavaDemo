package com.zane;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.RoleQueryDto;
import com.zane.entity.SysRole;
import com.zane.vo.SysRoleTreeVo;
import com.zane.vo.SysRoleVo;

import java.util.List;

public interface ISysRoleService {

    List<SysRoleVo> getRoleList();

    TableDataInfo<SysRoleVo> getRolePageList(Page page, RoleQueryDto roleQueryDto);

    SysRoleVo getRoleById(Long id);

    boolean updateByRole(SysRole role);

    boolean deleteById(Long id);

    boolean batchDelete(List<Long> ids);

    boolean addRole(SysRole role);

    List<SysRoleTreeVo> getRoleTree();
}
