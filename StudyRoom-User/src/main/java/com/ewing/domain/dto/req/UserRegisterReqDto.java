package com.ewing.domain.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: Ewing
 * @Date: 2024-10-10-17:58
 * @Description:
 */
@Data
public class UserRegisterReqDto {
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]{9}$")
    @NotBlank(message = "账号不能为空!")
    private String phone;

    @NotBlank(message = "昵称不能为空!")
    private String username;

    @NotBlank(message = "密码不能为空!")
    private String password;

    @NotBlank(message = "图形验证码不能为空!")
    @Pattern(regexp = "^[A-Za-z0-9]{4}$", message = "验证码格式不对!")
    private String imgVerityCode;

    @NotBlank
    @Length(min = 32, max = 32)
    private String sessionId;
}
