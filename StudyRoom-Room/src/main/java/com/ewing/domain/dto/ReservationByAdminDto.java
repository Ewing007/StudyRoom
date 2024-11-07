package com.ewing.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-11-06-21:52
 * @Description:
 */
@Data
public class ReservationByAdminDto {
//    private Long id;

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
}
