package com.zane.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserUpdateDto implements Serializable {
    private final static long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String nickName;

    private String email;

    private String phone;

    private String passWord;

    private Long deptId;

    private Integer status;

    /** 多角色 */
    private List<Long> roleIds;
}
