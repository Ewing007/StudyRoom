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
 * 座位表
 * @TableName seat_table
 */
@TableName(value ="seat_table")
@Data
public class SeatTable implements Serializable {
    /**
     * 座位ID
     */
    @TableId
    private String seatId;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 自习室ID，外键关联room表
     */
    private String roomId;

    /**
     * 座位编号，如“1A”、“2B”
     */
    private String seatNumber;

    /**
     * 座位状态，0-可用、1-已预约、2-维护中
     */
    private String status;

//    private Date date;

    private String delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 座位描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}