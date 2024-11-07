package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限表
 * @TableName permission_table
 */
@TableName(value ="permission_table")
@Data
public class PermissionTable implements Serializable {
    /**
     * 权限ID
     */
    @TableId
    private String permissionId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}