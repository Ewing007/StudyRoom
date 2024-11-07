package com.ewing.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Ewing
 * @Date: 2024-09-30-15:42
 * @Description:
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("校园自习室API接口文档")
                        // 接口文档简介
                        .description("校园自习室管理员服务接口文档")
                        // 接口文档版本
                        .version("0.0.1-SNAPSHOT")
                        // 开发者联系方式
                        .contact(new Contact().name("ewing")
                                .email("2742887864@qq.com"))
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("用户、角色、菜单、部门、字典等接口")
                        .url("http://127.0.0.1:8080"));
    }


}
