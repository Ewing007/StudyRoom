package com.ewing.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 时间槽表
 * @TableName time_slot_table
 */
@TableName(value ="time_slot_table")
@Data
public class TimeSlotTable implements Serializable {
    /**
     * 时间槽ID
     */
    @TableId
    private String slotId;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 时间段开始时间
     */
    private Date startTime;

    /**
     * 时间段结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private String delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}