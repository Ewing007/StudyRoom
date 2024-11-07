package com.ewing.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: Ewing
 * @Date: 2024-10-15-22:43
 * @Description:
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class FileInterceptor implements HandlerInterceptor {

    @Value("${StudyRoom.file.upload.path}")
    private String fileUploadPath;

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求uri
        String requestURI = request.getRequestURI();
        //设置过期时间
        response.setDateHeader("expires", System.currentTimeMillis() + 60 * 60 * 24 * 10 * 1000);
        try(OutputStream outputStream = response.getOutputStream(); InputStream input = new FileInputStream(
                fileUploadPath + requestURI
        );){
            byte[] b = new byte[4096];
            for(int n; (n = input.read(b)) != -1;) {
                outputStream.write(b,0, n);
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
