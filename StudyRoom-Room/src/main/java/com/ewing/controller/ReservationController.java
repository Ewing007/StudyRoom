package com.ewing.controller;

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
import com.ewing.service.ReservationTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-18-21:20
 * @Description:
 */
@RestController
@Tag(name = "自习室预约管理模块", description = "自习室预约管理接口")
@RequestMapping(ApiRouterConstant.ROOM_RESERVATION_URL_PREFIX)
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationTableService reservationTableService;

    @PostMapping("/book")
    public ResultPage<Void> bookRoom(@RequestBody @Validated BookRoomReqDto bookRoomReqDto) throws ParseException {
        return reservationTableService.bookRoom(bookRoomReqDto);
    }

    @PostMapping("/cancel/{reservationId}")
    public ResultPage<Void> cancelRoom(@PathVariable String reservationId) {
        return reservationTableService.cancelRoom(reservationId);
    }

    @PostMapping("/query")
    public ResultPage<PageRespDto<ReservationByUserDto>> queryRoom(@RequestBody @Validated ReservationByUserReqDto reservationByUserReqDto) throws ParseException {
        return reservationTableService.queryRoom(reservationByUserReqDto);
    }

    @PostMapping("/all_reservations")
    public ResultPage<PageRespDto<ReservationByAdminDto>> getAllReservations(@RequestBody @Validated GetAllReservationByAdminReqDto getAllReservationByAdminReqDto) {
        return reservationTableService.getAllReservations(getAllReservationByAdminReqDto);
    }

    @PutMapping("/update_reservation")
    public ResultPage<Void> updateReservation(@RequestBody @Validated ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto) {
        return reservationTableService.updateReservation(reservationUpdateByAdminReqDto);
    }

}
