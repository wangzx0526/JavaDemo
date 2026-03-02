package com.zane.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单详情/列表 VO
 */
@Data
public class SysMenuVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String menuName;
    /** 类型 M目录 C菜单 F按钮 */
    private String menuType;
    private String path;
    private String routeName;
    private String component;
    private String componentName;
    private String perms;
    private String icon;
    private Integer sort;
    /** 是否隐藏 0显示 1隐藏 */
    private Integer visible;
    /** 状态 0正常 1停用 */
    private Integer status;
    private Integer isCache;
    private Integer isFrame;
    private Date createTime;
    private Date updateTime;
}
