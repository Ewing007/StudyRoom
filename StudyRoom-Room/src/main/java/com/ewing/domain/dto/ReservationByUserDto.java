package com.ewing.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-11-07-12:02
 * @Description:
 */
@Data
public class ReservationByUserDto {

    private String reservationId;
    /**
     * 座位ID，外键关联seat表
     */
    private String seatId;

    private String roomId;

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


    private String status;



}
