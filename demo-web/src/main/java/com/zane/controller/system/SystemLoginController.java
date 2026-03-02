package com.zane.controller.system;

import com.zane.ISystemLoginService;
import com.zane.ISystemUserService;
import com.zane.dto.UserRegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "系统登录接口")
@RequestMapping("/system/")
public class SystemLoginController {
    private final ISystemLoginService systemLoginService;
    private final ISystemUserService systemUserService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        String token = systemLoginService.login(username, password);
        if (token == null) {
            return null;
        }
        return systemLoginService.login(username, password);
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Boolean register(@RequestBody UserRegisterDto user) {
        return systemLoginService.register(user);
    }

    @Operation(summary = "登出")
    @GetMapping("/logout")
    public void logout() {
        systemLoginService.logout();
    }
}
