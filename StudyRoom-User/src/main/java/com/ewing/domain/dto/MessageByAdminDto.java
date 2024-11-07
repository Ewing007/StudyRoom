package com.ewing.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ewing.domain.entity.MessageTable;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-11-07-12:54
 * @Description:
 */
@Data
public class MessageByAdminDto {
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

    // 用户名
    private String userName;

    // 用户角色列表
    private List<String> roles;

    // 回复列表
    private List<MessageTable> replies;


}
