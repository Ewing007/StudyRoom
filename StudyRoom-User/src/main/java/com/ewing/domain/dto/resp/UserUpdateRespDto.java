package com.ewing.domain.dto.resp;

import com.ewing.domain.dto.UserDto;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-10-13-10:38
 * @Description:
 */
@Data
@Builder
public class UserUpdateRespDto {

    private String message;

    private UserDto userDto;


}
