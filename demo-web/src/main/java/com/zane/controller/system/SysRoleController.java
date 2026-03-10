package com.zane.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zane.ISysRoleService;
import com.zane.core.domain.PageQuery;
import com.zane.core.domain.R;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.RoleQueryDto;
import com.zane.entity.SysRole;
import com.zane.vo.SysRoleTreeVo;
import com.zane.vo.SysRoleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "系统角色接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    private final ISysRoleService systemRoleService;

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    public R<List<SysRoleVo>> list() {
        return R.success(systemRoleService.getRoleList());
    }

    @Operation(summary = "分页查询角色")
    @GetMapping("/pagelist")
    public R<TableDataInfo<SysRoleVo>> pagelist(RoleQueryDto roleQueryDto, PageQuery pageQuery) {
        TableDataInfo<SysRoleVo> page = systemRoleService.getRolePageList(pageQuery.build(), roleQueryDto);
        return R.success(page);
    }

    @Operation(summary = "根据ID获取角色")
    @GetMapping("/{id}")
    public R<SysRoleVo> getRole(@PathVariable Long id) {
        return R.success(systemRoleService.getRoleById(id));
    }

    @Operation(summary = "修改角色信息")
    @SaCheckPermission("sys:role:update")
    @PostMapping("/update")
    public R<Boolean> update(@RequestBody SysRole role) {
        boolean result = systemRoleService.updateByRole(role);
        return R.success(result);
    }


    @Operation(summary = "新增角色")
    @SaCheckPermission("sys:role:add")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysRole role) {
        boolean result = systemRoleService.addRole(role);
        return R.success(result);
    }

    @Operation(summary = "删除角色")
    @SaCheckPermission("sys:role:delete")
    @DeleteMapping("/delete/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        boolean result = systemRoleService.deleteById(id);
        return R.success(result);
    }

    @Operation(summary = "批量删除角色")
    @SaCheckPermission("sys:role:delete")
    @DeleteMapping("/batchDelete")
    public R<Boolean> batchDelete(@RequestBody List<Long> ids) {
        boolean result = systemRoleService.batchDelete(ids);
        return R.success(result);
    }

    @Operation(summary = "获取角色数据（下拉框）")
    @GetMapping("/tree")
    public R<List<SysRoleTreeVo>> tree() {
        return R.success(systemRoleService.getRoleTree());
    }
}
