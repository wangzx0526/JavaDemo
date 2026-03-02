package com.zane.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 部门信息表
 * @TableName sys_dept
 */
@TableName(value ="sys_dept")
@Data
public class SysDept implements Serializable {
    /**
     * 部门ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父部门ID (顶级部门为 0)
     */
    private Long parentId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人ID (关联 sys_user 表 id，可选)
     */
    private Long leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态 (1正常 0停用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志 (0代表存在 1代表删除)
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}