package com.zane.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zane.ISystemDeptService;
import com.zane.core.domain.PageQuery;
import com.zane.core.domain.R;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.DeptQueryDto;
import com.zane.entity.SysDept;
import com.zane.vo.SysDeptTreeVo;
import com.zane.vo.SysDeptVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理
 *
 * @author Zane
 */
@Tag(name = "部门管理接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class SystemDeptController {
    private final ISystemDeptService systemDeptService;

    @Operation(summary = "获取部门列表")
    @GetMapping("/list")
    public R<List<SysDeptVo>> list(
            @RequestParam(required = false) String deptName,
            @RequestParam(required = false) Integer status) {
        return R.success(systemDeptService.getDeptList(deptName, status));
    }

    @Operation(summary = "分页查询部门")
    @GetMapping("/pagelist")
    public R<TableDataInfo<SysDeptVo>> pagelist(DeptQueryDto deptQueryDto, PageQuery pageQuery) {
        TableDataInfo<SysDeptVo> page = systemDeptService.getDeptPageList(pageQuery.build(), deptQueryDto);
        return R.success(page);
    }

    @Operation(summary = "根据ID获取部门")
    @GetMapping("/{id}")
    public R<SysDeptVo> get(@PathVariable Long id) {
        return R.success(systemDeptService.getDeptById(id));
    }

    @Operation(summary = "新增部门")
    @SaCheckPermission("sys:dept:add")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysDept dept) {
        boolean result = systemDeptService.addDept(dept);
        return R.success(result);
    }

    @Operation(summary = "修改部门")
    @SaCheckPermission("sys:dept:update")
    @PostMapping("/update")
    public R<Boolean> update(@RequestBody SysDept dept) {
        boolean result = systemDeptService.updateDept(dept);
        return R.success(result);
    }

    @Operation(summary = "删除部门")
    @SaCheckPermission("sys:dept:delete")
    @DeleteMapping("/delete/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        boolean result = systemDeptService.deleteById(id);
        return R.success(result);
    }

    @Operation(summary = "获取部门数据（下拉框使用）")
    @GetMapping("/tree")
    public R<List<SysDeptTreeVo>> tree() {
        return R.success(systemDeptService.getDeptTree());
    }
}
