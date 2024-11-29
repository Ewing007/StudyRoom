package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 自习室签到表
 * @TableName check_in_table
 */
@TableName(value ="check_in_table")
@Data
public class CheckInTable implements Serializable {
    /**
     * 签到ID，自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 二维码ID
     */
    private String qrCodeId;

    /**
     * 预约号，关联reservation_table表
     */
    private String reservationId;

    /**
     * 用户ID，关联user_table表
     */
    private String userId;

    /**
     * 签到时间
     */
    private Date checkInTime;

    /**
     * 签到二维码内容
     */
    private String checkInQrCode;

    /**
     * 签到状态 0-已签到,1-已签退,2-未签到
     */
    private String status;

    /**
     * 二维码失效时间
     */
    private Date expireTime;

    /**
     * 备注信息
     */
    private String notes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}