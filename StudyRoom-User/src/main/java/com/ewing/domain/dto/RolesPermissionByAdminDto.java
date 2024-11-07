package com.ewing.domain.dto;

import com.ewing.domain.entity.PermissionTable;
import com.ewing.domain.entity.RoleTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-11-01-19:48
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesPermissionByAdminDto {
    private RoleTable roleTable;

    private List<PermissionTable> permissionTableList;
}
