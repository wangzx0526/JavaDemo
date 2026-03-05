package com.zane.controller.system;

import com.zane.ISysMenuService;
import com.zane.core.domain.PageQuery;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.MenuQueryDto;
import com.zane.entity.SysMenu;
import com.zane.vo.SysMenuTreeVo;
import com.zane.vo.SysMenuVo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RBAC 权限管理 - 菜单管理
 *
 * @author Zane
 */
@Tag(name = "系统菜单接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/menu")
public class SystemMenuController {

    private final ISysMenuService sysMenuService;

    @Operation(summary = "获取菜单列表（扁平）")
    @GetMapping("/list")
    public List<SysMenuVo> list(
            @RequestParam(required = false) String menuName,
            @RequestParam(required = false) Integer status) {
        return sysMenuService.getList(menuName,  status);
    }

    @Operation(summary = "获取菜单树（树形，用于管理页与下拉）")
    @GetMapping("/tree")
    public List<SysMenuTreeVo> tree() {
        return sysMenuService.getMenuTree();
    }

    @Operation(summary = "根据 ID 获取菜单")
    @GetMapping("/{id}")
    public SysMenuVo get(@PathVariable Long id) {
        return sysMenuService.getMenuById(id);
    }

    @Operation(summary = "新增菜单")
    @PostMapping("/add")
    public Boolean add(@RequestBody SysMenu menu) {
        return sysMenuService.addMenu(menu);
    }

    @Operation(summary = "修改菜单")
    @PostMapping("/update")
    public Boolean update(@RequestBody SysMenu menu) {
        return sysMenuService.updateMenu(menu);
    }

    @Operation(summary = "删除菜单（存在子菜单时不可删除）")
    @DeleteMapping("/delete/{id}")
    public Boolean delete(@PathVariable Long id) {
        return sysMenuService.deleteById(id);
    }

    @Operation(summary = "根据用户ID获取菜单树（用于前端动态加载菜单）")
    @GetMapping("/userMenu")
    public List<SysMenuTreeVo> getUserMenu() {
        // 获取当前登录用户的ID
        return sysMenuService.getMenuTreeByUserId();
    }
}