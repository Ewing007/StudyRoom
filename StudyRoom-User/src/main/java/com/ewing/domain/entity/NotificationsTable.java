package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName notifications_table
 */
@TableName(value ="notifications_table")
@Data
public class NotificationsTable implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 通知状态,0-未读,1-已读
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 通知类型,system-系统通知,other-其他通知
     */
    private Object type;

    /**
     * 是否删除,0-未删除,1-已删除
     */
    private String isDeleted;

    /**
     * 通知优先级
     */
    private Object priority;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}