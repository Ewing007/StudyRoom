package com.ewing.domain.dto.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-13-14:03
 * @Description:
 */
@Data
public class RoomCreateReqDto {


    /**
     * 自习室名称
     */
    @NotBlank(message = "自习室名称不能为空")
    private String name;

    /**
     * 自习室位置描述
     */
    @NotBlank(message = "自习室位置描述不能为空")
    private String location;

    /**
     * 自习室容量
     */
    @NotNull(message = "自习室容量不能为空")
    private Integer capacity;

    /**
     * 自习室设施
     */
    private String amenities;


    /**
     * 开放时间
     */
    @NotEmpty(message = "开放时间不能为空")
    private List<String> openingHours;

    /**
     * 自习室描述
     */
    private String description;


    /**
     * 所在楼层
     */
    @NotBlank(message = "所在楼层不能为空")
    private String floor;

    /**
     * 自习室类型(0-免费、1-收费)
     */
    @NotBlank(message = "自习室类型不能为空")
    @Pattern(regexp = "^[01]$", message = "自习室类型只能为0或1")
    private String type;

    /**
     * 自习室图片URL
     */
    private String imageUrl;


}
