package com.ewing.domain.dto;

import lombok.Data;

import java.util.Set;

/**
 * @Author: Ewing
 * @Date: 2024-11-01-23:04
 * @Description:
 */
@Data
public class PermissionAllByAdminDto {

    private Set<String> permissionName;
}
