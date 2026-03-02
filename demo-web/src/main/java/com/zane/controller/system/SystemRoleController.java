package com.zane.controller.system;

import com.zane.ISystemRoleService;
import com.zane.core.domain.PageQuery;
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
public class SystemRoleController {

    private final ISystemRoleService systemRoleService;

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    public List<SysRoleVo> list() {
        return systemRoleService.getRoleList();
    }

    @Operation(summary = "分页查询角色")
    @GetMapping("/pagelist")
    public TableDataInfo<SysRoleVo> pagelist(RoleQueryDto roleQueryDto, PageQuery pageQuery) {
        return systemRoleService.getRolePageList(pageQuery.build(), roleQueryDto);
    }

    @Operation(summary = "根据ID获取角色")
    @GetMapping("/{id}")
    public SysRoleVo getRole(@PathVariable Long id) {
        return systemRoleService.getRoleById(id);
    }

    @Operation(summary = "修改角色信息")
    @PostMapping("/update")
    public boolean update(@RequestBody SysRole role) {
        return systemRoleService.updateByRole(role);
    }


    @Operation(summary = "新增角色")
    @PostMapping("/add")
    public boolean add(@RequestBody SysRole role) {
        return systemRoleService.addRole(role);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return systemRoleService.deleteById(id);
    }

    @Operation(summary = "批量删除角色")
    @DeleteMapping("/batchDelete")
    public boolean batchDelete(@RequestBody List<Long> ids) {
        return systemRoleService.batchDelete(ids);
    }

    @Operation(summary = "获取角色数据（下拉框）")
    @GetMapping("/tree")
    public List<SysRoleTreeVo> tree() {
        return systemRoleService.getRoleTree();
    }
}
