package com.zane.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import com.zane.ISysLoginService;
import com.zane.core.domain.R;
import com.zane.dto.UserRegisterDto;
import com.zane.vo.LoginInforVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "系统登录接口")
@RequestMapping("/system")
public class SysLoginController {
    private final ISysLoginService systemLoginService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public R<String> login(@RequestParam String username, @RequestParam String password) {
        String token = systemLoginService.login(username, password);
        if (token == null) {
            return R.fail("用户名或密码错误");
        }
        return R.success(token);
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody UserRegisterDto user) {
        boolean result = systemLoginService.register(user);
        return R.success(result);
    }

    @Operation(summary = "登出")
    @GetMapping("/logout")
    public R<Void> logout() {
        systemLoginService.logout();
        return R.success();
    }

    @SaIgnore
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/loginInfor")
    public R<LoginInforVo> getLoginInfor() {
        return R.success(systemLoginService.getLoginInforVo());
    }
}
