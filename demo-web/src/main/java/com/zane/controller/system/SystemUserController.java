package com.zane.controller.system;

import com.zane.ISystemUserService;
import com.zane.core.domain.PageQuery;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.QueryUserDto;
import com.zane.dto.UserCreateDto;
import com.zane.dto.UserUpdateDto;
import com.zane.entity.SysUser;
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

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public List<SysUserVo> getUserList() {
        return systemUserService.getList();
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/pagelist")
    public TableDataInfo<SysUserVo> list(QueryUserDto queryUserDto, PageQuery pageQuery) {
        return systemUserService.getPageList(pageQuery.build(), queryUserDto);
    }

    @Operation(summary = "获取用户")
    @GetMapping("/{id}")
    public SysUserVo get(@PathVariable Long id) {
        return systemUserService.getUserById(id);
    }


    @Operation(summary = "新增用户")
    @PostMapping("/add")
    public Boolean addUser(@RequestBody UserCreateDto user) {
        return systemUserService.insertUser(user);
    }

    @Operation(summary = "更新用户")
    @PostMapping("/update")
    public Boolean updateUser(@RequestBody UserUpdateDto user) {
        return systemUserService.updateUser(user);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/delete/{id}")
    public Boolean deleteUser(@PathVariable Long id) {
        return systemUserService.deleteUser(id);
    }

    // 批量删除
    @Operation(summary = "批量删除用户")
    @DeleteMapping("/batchDelete") //
    public Boolean batchDeleteUser(@RequestBody List<Long> ids) {
        return systemUserService.batchDeleteUser(ids);
    }
}
