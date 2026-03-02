package com.zane.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDto implements Serializable {
    /**
     * 用户名
      */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 确认密码
     */
    private String secondPassword;

    private static final long serialVersionUID = 1L;
}
