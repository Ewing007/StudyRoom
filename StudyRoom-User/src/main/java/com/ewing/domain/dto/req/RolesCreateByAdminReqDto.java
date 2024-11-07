package com.ewing.domain.dto.req;

import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-11-01-20:43
 * @Description:
 */
@Data
public class RolesCreateByAdminReqDto {
    private String roleId;
    private String roleName;
    private String roleDesc;
}
