package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色权限关联表
 * @TableName role_permissions
 */
@TableName(value ="role_permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissions implements Serializable {
    @TableId
    private Long id; // 自增主键

    private String roleId; // 角色ID
    private String permissionId; // 权限ID

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}