package com.ewing.manager;

import Utils.ImgVerifyCodeUtils;

import constant.CacheConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;

/**
 * @Author: Ewing
 * @Date: 2024-06-22-21:23
 * @Description:
 */
@Slf4j
@AllArgsConstructor
@Component
public class VerityCodeManager {

    private final StringRedisTemplate stringRedisTemplate;


    public String genImgVerityCode(String sessionId) throws IOException {
        String code = ImgVerifyCodeUtils.getRandomVerifyCode(4);
        String img = ImgVerifyCodeUtils.genVerifyCodeImg(code);
        stringRedisTemplate.opsForValue().set(CacheConstant.IMG_VERITY_CODE_CACHE_KEY + sessionId,
                code, Duration.ofMinutes(5)
                );
        return img;
    }


}
