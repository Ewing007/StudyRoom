package com.ewing.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Author: Ewing
 * @Date: 2024-10-27-20:10
 * @Description:
 */
@Data
public class SeatDto {
    /**
     * 座位ID
     */
    @TableId
    private String seatId;

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



}