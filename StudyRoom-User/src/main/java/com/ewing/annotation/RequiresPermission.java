package com.ewing.annotation;
import java.lang.annotation.*;
/**
 * @Author: Ewing
 * @Date: 2024-10-13-17:08
 * @Description: 自定义权限验证注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermission {
    /**
     * 需要的权限名称
     */
    String value();
}