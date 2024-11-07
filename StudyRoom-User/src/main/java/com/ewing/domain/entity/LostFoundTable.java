package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 失物遗失招领表
 * @TableName lost_found_table
 */
@TableName(value ="lost_found_table")
@Data
public class LostFoundTable implements Serializable {
    /**
     * 信息的唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发布信息的用户 ID，外键关联用户表
     */
    private String userId;

    /**
     * 物品类型（失物或招领）
     */
    private String itemType;

    /**
     * 物品描述
     */
    private String description;

    /**
     * 信息状态，1-表示有效，0-表示已处理
     */
    private String status;

    /**
     * 遗失或招领地点
     */
    private String location;

    /**
     * 物品图片URL
     */
    private String imageUrl;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 是否违规（0-否，1-是）
     */
    private String illegal;

    /**
     * 是否删除（0-否，1-是）
     */
    private String delIllegal;

    /**
     * 信息创建时间
     */
    private Date createTime;

    /**
     * 信息更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}