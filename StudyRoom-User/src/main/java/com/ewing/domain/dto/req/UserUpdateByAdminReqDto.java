package com.ewing.domain.dto.req;

import lombok.Data;

import java.util.Set;

/**
 * @Author: Ewing
 * @Date: 2024-11-01-16:38
 * @Description:
 */
@Data
public class UserUpdateByAdminReqDto {

    private String userId;

    private Integer creditScore;

    private Set<String> roles;

//    private Set<String> permissions;
}
