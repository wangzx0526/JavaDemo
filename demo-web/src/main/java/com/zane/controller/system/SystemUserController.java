package com.zane.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.zane.ISystemUserService;
import com.zane.core.domain.PageQuery;
import com.zane.core.domain.R;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.QueryUserDto;
import com.zane.dto.UserCreateDto;
import com.zane.dto.UserUpdateDto;
import com.zane.vo.SysUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统用户接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SystemUserController {
    private final ISystemUserService systemUserService;


    @SaIgnore
    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public R<List<SysUserVo>> getUserList() {
        return R.success(systemUserService.getList());
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/pagelist")
    public R<TableDataInfo<SysUserVo>> list(QueryUserDto queryUserDto, PageQuery pageQuery) {
        TableDataInfo<SysUserVo> page = systemUserService.getPageList(pageQuery.build(), queryUserDto);
        return R.success(page);
    }

    @SaCheckLogin
    @Operation(summary = "获取用户")
    @GetMapping("/{id}")
    public R<SysUserVo> get(@PathVariable Long id) {
        return R.success(systemUserService.getUserById(id));
    }


    @Operation(summary = "新增用户")
    @SaCheckPermission("sys:user:add")
    @PostMapping("/add")
    public R<Boolean> addUser(@RequestBody UserCreateDto user) {
        boolean result = systemUserService.insertUser(user);
        return R.success(result);
    }

    @Operation(summary = "更新用户")
    @SaCheckPermission("sys:user:update")
    @PostMapping("/update")
    public R<Boolean> updateUser(@RequestBody UserUpdateDto user) {
        boolean result = systemUserService.updateUser(user);
        return R.success(result);
    }

    @Operation(summary = "删除用户")
    @SaCheckPermission("sys:user:delete")
    @DeleteMapping("/delete/{id}")
    public R<Boolean> deleteUser(@PathVariable Long id) {
        boolean result = systemUserService.deleteUser(id);
        return R.success(result);
    }

    // 批量删除
    @Operation(summary = "批量删除用户")
    @SaCheckPermission("sys:user:delete")
    @DeleteMapping("/batchDelete") //
    public R<Boolean> batchDeleteUser(@RequestBody List<Long> ids) {
        boolean result = systemUserService.batchDeleteUser(ids);
        return R.success(result);
    }
}
