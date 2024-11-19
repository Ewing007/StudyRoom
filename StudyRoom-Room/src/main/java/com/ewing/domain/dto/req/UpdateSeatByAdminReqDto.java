package com.ewing.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

/**
 * @Author: Ewing
 * @Date: 2024-11-19-14:37
 * @Description:
 */
@Data
public class UpdateSeatByAdminReqDto {
    private String roomId;
    private String seatId;
    private String slotId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;
    private String seatStatus;
}
