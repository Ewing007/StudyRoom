package com.ewing.domain.dto.req;

import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-11-01-20:44
 * @Description:
 */
@Data
public class PermissionsCreateByAdminReqDto {
    private String permissionId;
    private String permissionName;
    private String permissionDesc;
}
