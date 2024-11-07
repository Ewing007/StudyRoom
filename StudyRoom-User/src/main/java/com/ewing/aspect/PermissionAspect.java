package com.ewing.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.ewing.annotation.RequiresPermission;
import com.ewing.domain.dto.UserDto;
import com.ewing.manager.RedisCache;
import com.ewing.service.UserTableService;
import constant.CacheConstant;
import constant.ErrorEnum;
import context.UserContext;
import context.UserInfoContextHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import Exception.BusinessException;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;

/**
 * @Author: Ewing
 * @Date: 2024-10-13-17:06
 * @Description: 权限验证切面
 */
@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final RedisCache redisCache;

    private final UserTableService userService;


    @Pointcut("@annotation(com.ewing.annotation.RequiresPermission)")
    public void permissionPointCut() {}


    @Around("permissionPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        }

        // 获取用户上下文
        UserContext userContext = UserInfoContextHandler.getUserContext();
        String userId = userContext.getUserId();
        if (ObjectUtil.isNull(userContext)) {
            throw new BusinessException(ErrorEnum.USER_LOGIN_EXPIRED);
        }

        // 从Redis获取用户信息
        UserDto user = redisCache.getCacheObject(CacheConstant.USERS_CACHE_KEY + userId);
        if (ObjectUtil.isNull(user)) {
            // 缓存未命中，从数据库加载并缓存
            user = userService.getUserDtoById(userId);
            if (user == null) {
                throw new BusinessException(ErrorEnum.USER_NOT_EXIT);
            }
            redisCache.setCacheObject(CacheConstant.USERS_CACHE_KEY + userId, user);
        }

        // 获取方法上的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
        String requiredPermission = requiresPermission.value();

        // 检查用户是否拥有该权限
        if (user.getPermissions() == null || !user.getPermissions().contains(requiredPermission)) {
            throw new BusinessException(ErrorEnum.USER_NOT_PERSSIONS);
        }

        // 有权限，继续执行方法
        return joinPoint.proceed();
    }
}
