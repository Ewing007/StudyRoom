package com.ewing.service.impl;

import Result.ResultPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ewing.domain.dto.resp.ImgVerityCodeRespDto;
import com.ewing.manager.VerityCodeManager;
import com.ewing.service.ImgVerityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 验证码服务实现类
 *
 * 本类提供了图像验证码生成的服务实现
 * 主要通过VerityCodeManager来生成验证码，并将其与一个唯一的会话ID关联起来
 *
 * @Author: Ewing
 * @Date: 2024-06-23-14:05
 * @Description: 图像验证码服务的具体实现
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VerityCodeServiceImpl implements ImgVerityService{

    // VerityCodeManager用于管理验证码的生成和验证
    private final VerityCodeManager verityCodeManager;

    // 会话ID，用于唯一标识一个验证码请求
    private final String sessionId = IdWorker.get32UUID();


    /**
     * 生成图像验证码
     *
     * 本方法通过VerityCodeManager生成一个图像验证码，并将其与一个会话ID关联起来
     * 返回包含验证码图片和会话ID的ImgVerityCodeRespDto对象
     *
     * @return 包含验证码图片和会话ID的响应对象
     * @throws IOException 如果生成验证码过程中发生错误
     */
    @Override
    public ResultPage<ImgVerityCodeRespDto> getImgVerityCode() throws IOException {
        // 使用预先生成的会话ID来生成验证码
        return ResultPage.SUCCESS(
                ImgVerityCodeRespDto.builder()
                        .sessionId(sessionId)
                        .img(verityCodeManager.genImgVerityCode(sessionId))
                        .build()
        );
    }

}

