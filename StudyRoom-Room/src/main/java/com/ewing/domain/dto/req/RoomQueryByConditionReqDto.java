package com.ewing.domain.dto.req;

import Page.PageReqDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-11-02-12:00
 * @Description:
 */
@Data
public class RoomQueryByConditionReqDto extends PageReqDto {
    //自习室名称
    private String name;
    //自习室容量
    private Integer capacity;
    //自习室设施
    private String amenities;
    //楼层
    private String floor;
    //自习室类型,0-免费、1-收费
    private String type;
    //当前状态（0-开放、1-维护中、2-关闭）
    private String currentStatus;
    //自习室位置描述
    private String location;
    //自习室描述
    private String description;
}






