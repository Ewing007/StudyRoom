package com.ewing.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-11-27-15:28
 * @Description:
 */
@Data
public class NotificationDto {

    /**
     * 通知ID
     */
    private String notificationId;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 接收者ID
     */
    private String recipientId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 通知类型,system-系统通知,other-其他通知
     */
    private Object type;
}
