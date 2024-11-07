package com.ewing.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

/**
 * @Author: Ewing
 * @Date: 2024-11-04-0:08
 * @Description:
 */
@Data
public class SeatViewReqDto {
    private String roomId;

    private String slotId;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;
}
