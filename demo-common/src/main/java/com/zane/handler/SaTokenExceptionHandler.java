package com.zane.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.SaTokenException;
import com.zane.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SaTokenExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public R<Void> handleNotLoginException(NotLoginException exception) {
        log.warn("sa-token not login: {}", exception.getMessage());
        return R.fail(401, exception.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    public R<Void> handleNotPermissionException(NotPermissionException exception) {
        log.warn("sa-token no permission: {}", exception.getPermission());
        return R.fail(403, "No permission: " + exception.getPermission());
    }

    @ExceptionHandler(SaTokenException.class)
    public R<Void> handleSaTokenException(SaTokenException exception) {
        log.warn("sa-token exception: {}", exception.getMessage());
        return R.fail(exception.getMessage());
    }
}
