package com.zane.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryUserDto implements Serializable {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}
