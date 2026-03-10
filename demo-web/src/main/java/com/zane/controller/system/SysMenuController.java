package com.zane.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zane.ISysMenuService;
import com.zane.core.domain.R;
import com.zane.entity.SysMenu;
import com.zane.vo.SysMenuTreeVo;
import com.zane.vo.SysMenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "系统菜单接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/menu")
public class SysMenuController {

    private final ISysMenuService sysMenuService;

    @Operation(summary = "获取菜单列表")
    @GetMapping("/list")
    public R<List<SysMenuVo>> list(
            @RequestParam(required = false) String menuName,
            @RequestParam(required = false) Integer status) {
        return R.success(sysMenuService.getList(menuName, status));
    }

    @Operation(summary = "获取菜单树")
    @GetMapping("/tree")
    public R<List<SysMenuTreeVo>> tree() {
        return R.success(sysMenuService.getMenuTree());
    }

    @Operation(summary = "根据ID获取菜单")
    @GetMapping("/{id}")
    public R<SysMenuVo> get(@PathVariable Long id) {
        return R.success(sysMenuService.getMenuById(id));
    }

    @Operation(summary = "新增菜单")
    @SaCheckPermission("sys:menu:add")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody SysMenu menu) {
        return R.success(sysMenuService.addMenu(menu));
    }

    @Operation(summary = "修改菜单")
    @SaCheckPermission("sys:menu:update")
    @PostMapping("/update")
    public R<Boolean> update(@RequestBody SysMenu menu) {
        return R.success(sysMenuService.updateMenu(menu));
    }

    @Operation(summary = "删除菜单")
    @SaCheckPermission("sys:menu:delete")
    @DeleteMapping("/delete/{id}")
    public R<Boolean> delete(@PathVariable Long id) {
        return R.success(sysMenuService.deleteById(id));
    }

    @Operation(summary = "获取当前用户菜单树")
    @GetMapping("/getRouters")
    public R<List<SysMenuTreeVo>> getUserMenu() {
        return R.success(sysMenuService.getMenuTreeByUserId());
    }
}
