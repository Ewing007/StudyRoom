package com.ewing.domain.dto.resp;

import com.ewing.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Ewing
 * @Date: 2024-10-09-21:16
 * @Description:
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRespDto {

    private UserDto userDto;

    private String token;



}
