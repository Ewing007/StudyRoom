package com.ewing.domain.dto.req;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-23:34
 * @Description:
 */
@Data
public class AnnouncementCreateReqDto {
    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;


    /**
     * 发布者用户名
     */
    private String authorName;

    /**
     * 公告发布时间
     */
    private Date createdTime;


    /**
     * 公告开始时间
     */
    private Date startTime;

    /**
     * 公告结束时间
     */
    private Date endTime;
}
