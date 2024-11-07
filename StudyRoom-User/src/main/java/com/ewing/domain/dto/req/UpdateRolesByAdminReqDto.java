package com.ewing.domain.dto.req;

import lombok.Data;

import java.util.Set;

/**
 * @Author: Ewing
 * @Date: 2024-11-01-20:58
 * @Description:
 */
@Data
public class UpdateRolesByAdminReqDto {

    private String roleId;

    private Set<String> permissions;

}
