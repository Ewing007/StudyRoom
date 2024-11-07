package com.ewing.domain.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-16-14:40
 * @Description:
 */
@Data
public class RoomUpdateReqDto {

    private String roomId;
    /**
     * 自习室名称
     */
    private String name;

    /**
     * 自习室位置描述
     */
    private String location;

    /**
     * 自习室容量
     */
    private Integer capacity;

    /**
     * 自习室设施
     */
    private String amenities;



    /**
     * 自习室描述
     */
    private String description;


    /**
     * 所在楼层
     */
    private String floor;

    /**
     * 自习室类型(0-免费、1-收费)
     */
    @Pattern(regexp = "^[01]$", message = "自习室类型只能为0或1")
    private String type;

    /**
     * 自习室图片URL
     */
    private String imageUrl;

    @Pattern(regexp = "^[012]$", message = "当前状态只能为0或1或2")
    private String currentStatus;

}