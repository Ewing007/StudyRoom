package com.ewing.interceptor;

import cn.hutool.core.util.ObjectUtil;


import context.UserContext;
import context.UserInfoContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 * @Author: Ewing
 * @Date: 2024-10-15-22:43
 * @Description:
 */

@Component
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {
    private static final String USER_ID_HEADER = "X-USER-ID";
    private static final String USERNAME_HEADER = "X-USERNAME";
    private static final String ROLE_HEADER = "X-PERMISSIONS";

    @Override
    public void apply(RequestTemplate template) {
        UserContext userContext = UserInfoContextHandler.getUserContext();
        if (ObjectUtil.isNotNull(userContext)) {
            template.header(USER_ID_HEADER, userContext.getUserId());
            template.header(USERNAME_HEADER, userContext.getUserName());
            template.header(ROLE_HEADER, String.join(",", userContext.getPermissions()));
            log.info("FeignClientInterceptor: 添加了用户id,用户名，权限请求头");
        } else {
            log.warn("FeignClientInterceptor: UserContext 为空");
        }
    }
}