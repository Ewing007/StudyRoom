package com.ewing.manager;

import cn.hutool.core.util.ObjectUtil;
import constant.CacheConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Ewing
 * @Date: 2024-06-22-21:19
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserVerityCodeManager {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * @description: 校验图形验证码
     * @param sessionId:
     * @param code:  :
     * @return boolean
     * @author: ewing
     * @date: 2024/6/22 21:40
     */
    public boolean imgVerityCodeOk(String sessionId, String code) {
        return ObjectUtil.equal(stringRedisTemplate.opsForValue()
                .get(CacheConstant.IMG_VERITY_CODE_CACHE_KEY + sessionId), code);
    }

    /**
     * @description: 删除验证码
     * @param sessionId:  :
     * @return void
     * @author: ewing
     * @date: 2024/6/22 21:42
     */
    public void removeImgVerityCode(String sessionId) {
        stringRedisTemplate.delete(CacheConstant.IMG_VERITY_CODE_CACHE_KEY + sessionId);
    }
    

}
