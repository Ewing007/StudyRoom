package com.ewing.domain.dto.req;

import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-21:47
 * @Description:
 */
@Data
public class LostFoundPersonaReqDto {

    private Long id;


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
}
