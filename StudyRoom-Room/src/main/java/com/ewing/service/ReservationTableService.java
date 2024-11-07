package com.ewing.service;

import Page.PageRespDto;
import Result.ResultPage;
import cn.hutool.db.PageResult;
import com.ewing.domain.dto.ReservationByAdminDto;
import com.ewing.domain.dto.ReservationByUserDto;
import com.ewing.domain.dto.req.BookRoomReqDto;
import com.ewing.domain.dto.req.GetAllReservationByAdminReqDto;
import com.ewing.domain.dto.req.ReservationByUserReqDto;
import com.ewing.domain.dto.req.ReservationUpdateByAdminReqDto;
import com.ewing.domain.entity.ReservationTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;

/**
* @author ewing
* @description 针对表【reservation_table(自习室座位预约表)】的数据库操作Service
* @createDate 2024-10-15 20:30:49
*/
public interface ReservationTableService extends IService<ReservationTable> {


    ResultPage<Void> bookRoom(BookRoomReqDto bookRoomReqDto) throws ParseException;

    ResultPage<PageRespDto<ReservationByUserDto>> queryRoom(ReservationByUserReqDto reservationByUserReqDto);

    ResultPage<Void> cancelRoom(String reservationId);

    ResultPage<PageRespDto<ReservationByAdminDto>> getAllReservations(GetAllReservationByAdminReqDto getAllReservationByAdminReqDto);

    ResultPage<Void> updateReservation(ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto);
}
