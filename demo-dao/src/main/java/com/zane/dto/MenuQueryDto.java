package com.zane.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuQueryDto implements Serializable {

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单状态 (0正常 1停用)
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}