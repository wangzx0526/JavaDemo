package com.zane.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统菜单表
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID（0为顶级）
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 类型 M目录 C菜单 F按钮
     */
    private String menuType;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 路由名称（必须唯一）
     */
    private String routeName;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 组件名称（KeepAlive用）
     */
    private String componentName;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否隐藏 0显示 1隐藏
     */
    private Integer visible;

    /**
     * 状态 0正常 1停用
     */
    private Integer status;

    /**
     * 是否缓存 0否 1是
     */
    private Integer isCache;

    /**
     * 是否外链 0否 1是
     */
    private Integer isFrame;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}