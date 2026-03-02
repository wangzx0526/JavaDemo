package com.zane.controller.system;

import com.zane.ISystemDeptService;
import com.zane.core.domain.PageQuery;
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
    public List<SysDeptVo> list(
            @RequestParam(required = false) String deptName,
            @RequestParam(required = false) Integer status) {
        return systemDeptService.getDeptList(deptName, status);
    }

    @Operation(summary = "分页查询部门")
    @GetMapping("/pagelist")
    public TableDataInfo<SysDeptVo> pagelist(DeptQueryDto deptQueryDto, PageQuery pageQuery) {
        return systemDeptService.getDeptPageList(pageQuery.build(), deptQueryDto);
    }

    @Operation(summary = "根据ID获取部门")
    @GetMapping("/{id}")
    public SysDeptVo get(@PathVariable Long id) {
        return systemDeptService.getDeptById(id);
    }

    @Operation(summary = "新增部门")
    @PostMapping("/add")
    public Boolean add(@RequestBody SysDept dept) {
        return systemDeptService.addDept(dept);
    }

    @Operation(summary = "修改部门")
    @PostMapping("/update")
    public Boolean update(@RequestBody SysDept dept) {
        return systemDeptService.updateDept(dept);
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable Long id) {
        return systemDeptService.deleteById(id);
    }

    @Operation(summary = "获取部门数据（下拉框使用）")
    @GetMapping("/tree")
    public List<SysDeptTreeVo> tree() {
        return systemDeptService.getDeptTree();
    }
}
