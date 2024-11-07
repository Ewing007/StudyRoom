package com.ewing.domain.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-0:48
 * @Description:
 */
@Data
public class UserDeleteReqDto {
    @NotBlank(message = "用户id不能为空!")
    private String userId;
}
