package com.ewing.domain.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-10-09-21:18
 * @Description:
 */
@Data
public class TeacherReqDto {

    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]{9}$")
    @NotBlank(message = "账号不能为空!")
    private String username;

    @NotBlank(message = "密码不能为空!")
    private String password;

    @NotBlank(message = "图形验证码不能为空!")
    @Pattern(regexp = "^\\d{4}$", message = "验证码格式不对!")
    private String imgVerityCode;
}
