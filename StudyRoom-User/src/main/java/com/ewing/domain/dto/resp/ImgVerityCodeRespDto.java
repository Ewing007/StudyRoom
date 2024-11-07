package com.ewing.domain.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-06-23-14:03
 * @Description: 图形验证码响应 DTO
 */
@Data
@Builder
public class ImgVerityCodeRespDto {

    private String sessionId;

    private String img;
}
