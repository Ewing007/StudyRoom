package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 自习室开放时间段表
 * @TableName room_time_slot_table
 */
@TableName(value ="room_time_slot_table")
@Data
public class RoomTimeSlotTable implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 自习室ID，关联 room_table
     */
    private String roomId;

    /**
     * 时间段ID，关联 time_slot_table
     */
    private String slotId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}