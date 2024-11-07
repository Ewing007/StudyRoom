package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.mapping.FetchType;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user_table
 */
@TableName(value ="user_table")
@Data
public class UserTable implements Serializable {
    /**
     * 用户id
     */
    @TableId
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



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}