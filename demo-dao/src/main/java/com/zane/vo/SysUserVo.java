package com.zane.vo;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SysUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户米
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 角色id
     */
    private List<Long> roleIds;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
