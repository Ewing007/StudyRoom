package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

/**
 * 座位时间段表
 * @TableName seat_time_table
 */
@TableName(value ="seat_time_table")
@Data
public class SeatTimeTable implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String roomId;
    /**
     * 座位ID，关联 seat_table
     */
    private String seatId;

    private String seatNumber;
    /**
     * 时间段ID，关联 time_slot_table
     */
    private String slotId;

    /**
     * 状态，0-可用，1-已预约，2-维护中
     */
    private String status;

    /**
     * 预约日期
     */
    private LocalDate date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}