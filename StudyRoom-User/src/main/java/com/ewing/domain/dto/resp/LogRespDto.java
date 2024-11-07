package com.ewing.domain.dto.resp;

import Page.PageRespDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-11-22:42
 * @Description:
 */
@Data
@Builder
public class LogRespDto{
    private Long id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String operationUser;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求的方法名
     */
    private String method;

    /**
     * 请求方式（如：POST, GET等）
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 方法响应参数
     */
    private String responseResult;

    /**
     * 操作用户的IP地址
     */
    private String ip;

    /**
     * IP归属地
     */
    private String ipLocation;

    /**
     * 操作状态（1为成功，0为失败）
     */
    private String status;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * 方法执行耗时（单位：毫秒）
     */
    private Long takeTime;

    /**
     * 操作时间，默认为当前时间
     */
    private Date operationTime;
}
