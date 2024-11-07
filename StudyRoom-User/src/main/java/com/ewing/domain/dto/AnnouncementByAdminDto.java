package com.ewing.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-31-21:31
 * @Description:
 */
@Data
public class AnnouncementByAdminDto {

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
     * 发布者ID，关联管理员
     */
    private String authorId;

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

    private Date updateTime;
}
