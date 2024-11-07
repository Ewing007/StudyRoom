package com.ewing.domain.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-14:00
 * @Description:
 */
@Data
public class MessageReqDto {

    /**
     * 留言的正文内容
     */
    @NotBlank(message = "留言内容不能为空!")
    private String content;
}
