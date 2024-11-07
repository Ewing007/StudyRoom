package com.ewing.controller;

import Result.ResultPage;
import com.ewing.domain.dto.resp.ImgVerityCodeRespDto;
import com.ewing.service.impl.VerityCodeServiceImpl;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

/**
 * @Author: Ewing
 * @Date: 2024-10-10-20:23
 * @Description:
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "图形验证码管理模块", description = "图形验证码接口")
@RequestMapping(ApiRouterConstant.IMAGE_CODE_URL_PREFIX)
public class ImageController {

    private final VerityCodeServiceImpl imgVerityCodeService;

    @Operation(summary = "图形验证码发送接口")
    @GetMapping("/img_verity_code")
    public ResultPage<ImgVerityCodeRespDto> getImgVerityCode() throws IOException {
        return imgVerityCodeService.getImgVerityCode();
    }
}
