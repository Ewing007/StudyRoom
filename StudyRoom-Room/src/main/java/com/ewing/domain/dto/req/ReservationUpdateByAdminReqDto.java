package com.ewing.domain.dto.req;

import lombok.Data;

/**
 * @Author: Ewing
 * @Date: 2024-11-06-22:14
 * @Description:
 */
@Data
public class ReservationUpdateByAdminReqDto {

    private String reservationId;
    /**
     * 预约状态 0-已确认,1-已取消,2-完成,3-违约
     */
    private String status;

    private String delFlag;

    /**
     * 备注信息
     */
    private String notes;
}
