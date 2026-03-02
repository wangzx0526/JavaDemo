package com.zane.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeptQueryDto implements Serializable {

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门状态 (1正常 0停用)
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}
