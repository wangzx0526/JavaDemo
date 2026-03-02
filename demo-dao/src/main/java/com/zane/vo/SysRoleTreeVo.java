package com.zane.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleTreeVo implements Serializable {
    private final static long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;
}
