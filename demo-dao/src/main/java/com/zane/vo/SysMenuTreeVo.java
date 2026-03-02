package com.zane.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树 VO（用于树形展示与下拉）
 */
@Data
public class SysMenuTreeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String menuName;
    /** 类型 M目录 C菜单 F按钮 */
    private String menuType;
    private String path;
    private String icon;
    private Integer sort;
    private Integer visible;
    private Integer status;
    /** 子菜单 */
    private List<SysMenuTreeVo> children;
}
