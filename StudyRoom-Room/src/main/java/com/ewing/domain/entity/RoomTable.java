package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 自习室表
 * @TableName room_table
 */
@TableName(value ="room_table")
@Data
public class RoomTable implements Serializable {
    /**
     * 自习室ID
     */
    @TableId
    private String roomId;

    /**
     * 主键ID自增主键
     */
    private Long id;

    private String userId;

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
     * 当前状态（0-开放、1-维护中、2-关闭）
     */
    private String currentStatus;

    /**
     * 开放时间
     */
//    private String openingHours;

    /**
     * 自习室描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 所在楼层
     */
    private String floor;

    /**
     * 自习室类型(0-免费、1-收费)
     */
    private String type;

    private String delFlag;

    /**
     * 自习室图片URL
     */
    private String imageUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}