package com.ewing.domain.dto.req;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-23:33
 * @Description:
 */
@Data
public class AnnouncementUpdateReqDto {

    private Long id;
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
     * 公告状态，1为已发布，0为草稿
     */
    private String status;

    /**
     * 公告开始时间
     */
    private Date startTime;

    /**
     * 公告结束时间
     */
    private Date endTime;
}
