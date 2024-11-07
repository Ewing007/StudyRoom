package com.ewing.domain.dto.resp;

import com.ewing.domain.dto.UserDto;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-10-10-19:46
 * @Description:
 */
@Data
@Builder
public class UserRegisterRespDto {

    private String message;

    private UserDto userDto;

    private String token;
}
