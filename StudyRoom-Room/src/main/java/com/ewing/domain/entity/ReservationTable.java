package com.ewing.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自习室座位预约表
 * @TableName reservation_table
 */
@TableName(value ="reservation_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationTable implements Serializable {
    /**
     * 预约ID，自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private String reservationId;

    private String slotId;
    /**
     * 座位ID，外键关联seat表
     */
    private String seatId;

    private String roomId;
    /**
     * 用户ID，外键关联user表
     */
    private String userId;

    private Date date;
    /**
     * 预约开始时间
     */
    private Date startTime;

    /**
     * 预约结束时间
     */
    private Date endTime;

    /**
     * 预约创建时间
     */
    private Date reservationTime;

    private Date cancelTime;

    private Date finishTime;

    private Date penaltyTime;

    private Date payTime;

    private Date cancelPayTime;
    /**
     * 预约状态 0-已确认,1-已取消,2-完成,3-违约
     */
    private String status;

    private String delFlag;

    private String paymentStatus;

    private String checkInStatus;

    /**
     * 备注信息
     */
    private String notes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}