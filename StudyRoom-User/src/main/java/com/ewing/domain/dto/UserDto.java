package com.ewing.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author: Ewing
 * @Date: 2024-10-10-0:19
 * @Description:
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    /**
     * 用户id
     */
    @TableId
    private String userId;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 信用分
     */
    private Integer creditScore;

    private Boolean isLogin;

    private Set<String> roles;

    // 用户拥有的权限
    private Set<String> permissions;
}
