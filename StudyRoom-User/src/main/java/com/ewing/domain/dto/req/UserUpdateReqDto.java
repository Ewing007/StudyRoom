package com.ewing.domain.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: Ewing
 * @Date: 2024-10-12-17:40
 * @Description:
 */
@Data
public class UserUpdateReqDto {

//    /**
//     * 用户id
//     */
//    @NotBlank(message = "用户id不能为空!")
//    private String userId;

    /**
     * 用户名
     */
    @Length(min = 2,max = 10, message = "用户名字数超出限制2~10")
    private String userName;


    /**
     * 邮箱
     */
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$")
    private String email;

    /**
     * 手机号
     */
    @NotBlank
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]{9}$")
    private String phoneNumber;

    /**
     * 用户性别（0男，1女，2未知）
     */
    @NotBlank(message = "性别不能为空!")
    @Pattern(regexp = "^[0-2]$")
    private String sex;

    /**
     * 头像
     */
    @Pattern(regexp = "^.*\\.(png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|bpm|BPM)$", message = "图片格式只能为png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|bpm|BPM!")
    private String avatar;





}
