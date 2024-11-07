package com.ewing.domain.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-14:05
 * @Description:
 */
@Data
public class MessageReplyReqDto {
    @NotBlank(message = "回复的用户ID不能为空!")
    private String replyToUserId;
    @NotBlank(message = "回复的用户名不能为空!")
    private String replyToUserName;
    /**
     * 留言的正文内容
     */
    @NotBlank(message = "留言内容不能为空!")
    private String content;

    /**
     * 回复的留言 ID，NULL 表示不是回复
     */
    @NotNull(message = "回复的留言ID不能为空!")
    private String replyTo;
}
