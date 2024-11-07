package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 * @TableName role_table
 */
@TableName(value ="role_table")
@Data
public class RoleTable implements Serializable {
    /**
     * 角色id
     */
    @TableId
    private String roleId;

    /**
     * ID自增
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}