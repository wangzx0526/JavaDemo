package com.zane.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用响应结果封装
 *
 * @param <T> 数据泛型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 业务数据
     */
    private T data;

    /**
     * 使用指定枚举构建成功响应
     */
    public static <T> R<T> success(ResultCodeEnum resultCode, T data) {
        return new R<>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    /**
     * 默认成功（无返回数据）
     */
    public static <T> R<T> success() {
        return success(null);
    }

    /**
     * 默认成功（有返回数据）
     */
    public static <T> R<T> success(T data) {
        return new R<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 自定义成功信息
     */
    public static <T> R<T> success(String message, T data) {
        return new R<>(ResultCodeEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 使用指定枚举构建失败响应
     */
    public static <T> R<T> fail(ResultCodeEnum resultCode) {
        return new R<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    /**
     * 默认失败
     */
    public static <T> R<T> fail() {
        return fail(ResultCodeEnum.FAIL);
    }

    /**
     * 自定义失败信息（默认失败状态码）
     */
    public static <T> R<T> fail(String message) {
        return new R<>(ResultCodeEnum.FAIL.getCode(), message, null);
    }

    /**
     * 完全自定义失败信息
     */
    public static <T> R<T> fail(int code, String message) {
        return new R<>(code, message, null);
    }
}

