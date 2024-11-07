package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告表
 * @TableName announcement_table
 */
@TableName(value ="announcement_table")
@Data
public class AnnouncementTable implements Serializable {
    /**
     * 公告ID，自增主键
     */
    @TableId(type = IdType.AUTO)
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}