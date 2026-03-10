package com.zane.vo;

import com.zane.entity.SysDept;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class LoginInforVo implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 用户
     */
    private SysUserVo sysUser;

    /**
     * 部门
     */
    private SysDept sysDept;

    /**
     * 菜单权限
     */
    private Set<String> menuPermission;

    /**
     * 角色权限
     */
    private Set<String> roleCodes;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 用户类型
     */
    private String userType;
}
