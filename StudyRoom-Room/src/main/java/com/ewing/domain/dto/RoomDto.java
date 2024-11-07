package com.ewing.domain.dto;

import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-10-15-23:01
 * @Description:
 */
@Data
public class RoomDto {
    private String roomId;
    private String name;
    private String location;
    private Integer capacity;
    private String amenities;
    private String currentStatus; // 0-开放、1-维护中、2-关闭
    private String openingHours;
    private String description;
    private String floor;
    private String type; // 0-免费、1-收费
    private String imageUrl;
}
