package com.zane.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author Zane
 * @since 2026-02-12
 */
@Data
@TableName("sys_user")
public class SysUser {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String userName;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickName;

    /**
     * 密码
     */
    @TableField("password")
    private String passWord;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 状态 0-禁用 1-启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 逻辑删除标识 0-未删除 1-已删除
     */
    @TableLogic
    @TableField("deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}