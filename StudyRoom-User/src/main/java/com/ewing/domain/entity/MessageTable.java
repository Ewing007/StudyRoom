package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 留言表
 * @TableName message_table
 */
@TableName(value ="message_table")
@Data
public class MessageTable implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String messageId;

    private String topMessageId;
    /**
     * 用户ID，外键关联user表
     */
    private String userId;

    /**
     * 留言的正文内容
     */
    private String content;

    /**
     * 回复的留言 ID，NULL 表示不是回复
     */
    private String replyTo;

    /**
     * 留言状态，1 表示正常，0 表示删除
     */
    private String status;


    /**
     * 留言创建时间
     */
    private Date createTime;

    /**
     * 留言更新时间
     */
    private Date updateTime;

    private String replyToUserId;

    private String replyToUserName;

    private String userAvatar;

    private String delFlag;

    // 非数据库字段，用于携带用户信息
    @TableField(exist = false)
    private String userName; // 用户名

    @TableField(exist = false)
    private List<String> roles; // 用户角色列表

    @TableField(exist = false)
    private List<MessageTable> replies; // 回复列表

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}