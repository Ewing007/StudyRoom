package com.ewing.domain.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-14-16:54
 * @Description:
 */
@Data
public class LostFoundReqDto {


    /**
     * 物品类型（失物或招领）
     */
    @NotBlank(message = "物品类型不能为空!")
    @Pattern(regexp = "^[0-1]$")
    private String itemType;

    /**
     * 物品描述
     */
    @NotBlank(message = "物品描述不能为空!")
    private String description;

    /**
     * 遗失或招领地点
     */
    @NotBlank(message = "物品类型不能为空!")
    private String location;

    /**
     * 物品图片URL
     */
//    @Pattern(regexp = "^.*\\.(png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|bpm|BPM)$")
//    @NotBlank(message = "图片格式只能为png|PNG|jpg|JPG|jpeg|JPEG|gif|GIF|bpm|BPM!")
    private String imageUrl;

    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式不能为空!")
    private String contactInfo;




}
