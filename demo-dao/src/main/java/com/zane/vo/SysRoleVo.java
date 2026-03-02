package com.zane.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysRoleVo implements Serializable {
    private final static long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;
}
