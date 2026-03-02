package com.zane.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysDeptTreeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long id;

    /**
     * 父部门ID (顶级部门为 0)
     */
    private Long parentId;

    /**
     * 部门名称
     */
    private String deptName;
}
