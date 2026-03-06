package com.zane.core.domain;

/**
 * 通用业务响应码枚举
 */
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    FAIL(500, "操作失败");

    private final int code;
    private final String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

