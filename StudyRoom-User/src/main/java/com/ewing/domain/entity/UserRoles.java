package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关联表
 * @TableName user_roles
 */
@TableName(value ="user_roles")
@Data
public class UserRoles implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}