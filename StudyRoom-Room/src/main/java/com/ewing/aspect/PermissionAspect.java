package com.ewing.aspect;

import Exception.BusinessException;
import cn.hutool.core.util.ObjectUtil;
import com.ewing.annotation.RequiresPermission;
import com.ewing.context.UserContext;
import com.ewing.context.UserContextHolder;

import com.ewing.manager.RedisCache;

import constant.ErrorEnum;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-13-17:06
 * @Description: 权限验证切面
 */
@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {




    @Pointcut("@annotation(com.ewing.annotation.RequiresPermission)")
    public void permissionPointCut() {}



    @Around("permissionPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        UserContext userContext = UserContextHolder.getUserContext();

        if (ObjectUtil.isNull(userContext)) {
            throw new BusinessException(ErrorEnum.USER_LOGIN_EXPIRED);
        }

        String userId = userContext.getUserId();
        // 这里直接从 userContext 获取权限
        List<String> permissions = userContext.getPermissions();

        // 获取方法上的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
        String requiredPermission = requiresPermission.value();

        // 检查用户是否拥有该权限
        if (permissions == null || !permissions.contains(requiredPermission)) {
            throw new BusinessException(ErrorEnum.USER_NOT_PERSSIONS);
        }

        // 有权限，继续执行方法
        return joinPoint.proceed();
    }
}
