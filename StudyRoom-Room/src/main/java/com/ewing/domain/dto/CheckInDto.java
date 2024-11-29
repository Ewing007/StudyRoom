package com.ewing.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-11-29-15:00
 * @Description:
 */
@Data
public class CheckInDto {

    /**
     * 二维码ID
     */
    private String qrCodeId;


    /**
     * 用户ID，关联user_table表
     */
    private String userId;


    /**
     * 签到二维码内容
     */
    private String checkInQrCode;

    private String content;

}
