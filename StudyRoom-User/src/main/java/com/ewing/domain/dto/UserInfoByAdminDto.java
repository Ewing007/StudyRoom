package com.ewing.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @Author: Ewing
 * @Date: 2024-11-01-11:08
 * @Description:
 */
@Data
public class UserInfoByAdminDto {

    private String userId;

    /**
     * ID自增
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;


    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private String delFlag;

    private Set<String> roles;

    private Set<String> permissions;
}
