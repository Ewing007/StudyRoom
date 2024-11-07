package com.ewing.filter;

import com.ewing.context.UserContext;
import com.ewing.context.UserContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-16-11:03
 * @Description:
 */
@Slf4j
@Component
public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String userId = httpRequest.getHeader("X-USER-ID");
            String username = httpRequest.getHeader("X-USERNAME");
            String permission = httpRequest.getHeader("X-PERMISSIONS");

            log.info("UserContextFilter: Received userId={}, username={}, permission={}", userId, username, permission);
            if (userId != null && username != null && permission != null) {
                List<String> permissions = Arrays.asList(permission.split(","));
                UserContext userContext = new UserContext(userId, username,permissions );
                UserContextHolder.setUserContext(userContext);
                System.out.println("UserContextFilter: Set UserContext");
            } else {
                System.out.println("UserContextFilter: Missing user headers");
            }

            chain.doFilter(request, response);
        } finally {
            // 确保在请求完成后清理 ThreadLocal 变量
            UserContextHolder.clear();
            System.out.println("UserContextFilter: Cleared UserContext");
        }
    }
}
