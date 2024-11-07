package com.ewing.domain.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-17:06
 * @Description:
 */
@Data
public class LostFoundDto {
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
     * 信息创建时间
     */
    private Date createTime;

    /**
     * 信息更新时间
     */
    private Date updateTime;
}
