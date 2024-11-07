package com.ewing.domain.dto;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-13:40
 * @Description:
 */


import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class MessageDto {
//    private Long id; // 留言ID
//    private String userId; // 用户ID
//    private String userName; // 用户名
//    private List<String> roles; // 用户角色列表
//    private String content; // 留言内容
//    private Long replyTo; // 回复的留言ID
//    private String status; // 留言状态
//    private Date createTime; // 创建时间
//    private Date updateTime; // 更新时间
//    private List<MessageDto> replies; // 回复列表

    private String messageId;
    private String userId;
    private String userName;
    private String userAvatar;
    private String content;
    private String replyTo;
    private String replyToUserId;
    private String replyToUserName;
    private String createTime;
    private String updateTime;
    private List<MessageDto> children;
}

