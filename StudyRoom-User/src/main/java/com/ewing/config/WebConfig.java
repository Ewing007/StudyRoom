package com.ewing.config;

import com.ewing.interceptor.FeignClientInterceptor;
import com.ewing.interceptor.FileInterceptor;
import com.ewing.interceptor.TokenInterceptor;
import constant.SystemConfigConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Ewing
 * @Date: 2024-06-24-15:59
 * @Description:
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${StudyRoom.file.upload.path}")
    private String fileUploadPath;


    private final FileInterceptor fileInterceptor;
    private final TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fileInterceptor)
                .addPathPatterns(SystemConfigConstant.FILE_UPLOAD_DIRECTORY + "**")
                .order(1);
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/home",
                        "/api/public/**",
                        "/api/front/user/register",
                        "/api/front/user/login",
                        "/api/front/image/**",
                        "/doc.html",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/swagger-ui/**",
                        "/api/front/user/rooms", //放行获取自习室信息
                        "/api/front/announcement/all", //放行获取公告信息
                        "/DataSource/StudyRoom/UploadFile/**",
                        "/error"
                );

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Knife4j 静态资源
        registry.addResourceHandler(fileUploadPath.substring(fileUploadPath.indexOf('/')) + SystemConfigConstant.FILE_UPLOAD_DIRECTORY +  "**")
                .addResourceLocations("file:" + fileUploadPath + SystemConfigConstant.FILE_UPLOAD_DIRECTORY);
        registry.addResourceHandler("/doc.html**")
                .addResourceLocations("classpath:/META-INF/resources/doc.html");
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
    }
}
