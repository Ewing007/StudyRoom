package com.ewing.domain.dto.req;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

/**
 * @Author: Ewing
 * @Date: 2024-10-18-22:39
 * @Description:
 */
@Data
public class BookRoomReqDto {

    private String roomId;

    private String seatId;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;


    private Set<String> time;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    private Date startTime;
//
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    private Date endTime;
}
