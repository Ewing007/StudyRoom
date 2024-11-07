package com.ewing.interceptor;

import Utils.JwtUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ewing.domain.dto.UserDto;
import com.ewing.manager.RedisCache;
import com.ewing.manager.UserInfoCacheManager;
import constant.CacheConstant;
import constant.ErrorEnum;
import constant.SystemConfigConstant;
import context.UserContext;
import context.UserInfoContextHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import Exception.BusinessException;
/**
 * @Author: Ewing
 * @Date: 2024-10-11-13:05
 * @Description:
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    private final UserInfoCacheManager userInfoCacheManager;

    private final RedisCache redisCache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取登录JWT
        String token = request.getHeader(SystemConfigConstant.HTTP_AUTH_HEADER_NAME);

        log.info("token:{}",token);
        //解析token
        if (!StringUtils.hasText(token)) {
            throw new BusinessException(ErrorEnum.USER_LOGIN_EXPIRED);
        }

        String userId = JwtUtils.parseToken(token, SystemConfigConstant.STUDY_ROOM_FRONT_KEY);
        if(ObjectUtil.isNull(userId)) {
            throw new  BusinessException(ErrorEnum.USER_LOGIN_EXPIRED);
        }

        log.info("userId {}",userId);
        //从redis获取token 比较token存在 用户在线
        if(!ObjectUtil.equal(token,redisCache.getCacheObject(CacheConstant.TOKEN_VERITY_CACHE_KEY + userId))) {
            log.info("开始从redis对比token");
            throw new BusinessException(ErrorEnum.USER_LOGIN_EXPIRED);
        }

        log.info("redis对比token成功，获取用户信息");
        UserDto userInfo = (UserDto) redisCache.getCacheObject(CacheConstant.USERS_CACHE_KEY + userId);
//        UserDto userInfo = userInfoCacheManager.getUser(userId);

        log.info("userInfo:{}",userInfo);
        log.info("userId {}",userId);
        if(ObjectUtil.isNull(userInfo)) {
            throw new BusinessException(ErrorEnum.USER_NOT_EXIT);
        }

        UserContext userContext = BeanUtil.copyProperties(userInfo, UserContext.class);
        log.info("userContext:{}",userContext.toString());
        UserInfoContextHandler.setUserContext(userContext);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("开始清理InfoContex内容!");
        UserInfoContextHandler.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
