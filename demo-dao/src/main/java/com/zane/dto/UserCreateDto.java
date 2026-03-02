package com.zane.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class UserCreateDto {

    private String userName;

    private String nickName;

    private String email;

    private String phone;

    private String passWord;

    private Long deptId;

    /** 多角色 */
    private List<Long> roleIds;
}
