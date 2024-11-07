package com.ewing.feign;

/**
 * @Author: Ewing
 * @Date: 2024-10-16-0:16
 * @Description:
 */

import com.ewing.domain.dto.req.*;
import constant.ApiRouterConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// Feign Client 用于调用自习室管理微服务的接口
@FeignClient(name = "StudyRoom-Room")
public interface RoomClient {

    @PostMapping(ApiRouterConstant.ROOM_URL_PREFIX + "/create")
    String createStudyRoom(@RequestBody RoomCreateReqDto room);

    @PutMapping(ApiRouterConstant.ROOM_URL_PREFIX + "/updateRoom")
    String updateStudyRoom( @RequestBody RoomUpdateReqDto roomTable);

    @PostMapping(ApiRouterConstant.ROOM_RESERVATION_URL_PREFIX + "/book")
    String bookStudyRoom(BookRoomReqDto bookRoomReqDto);

    @PostMapping(ApiRouterConstant.ROOM_RESERVATION_URL_PREFIX + "/cancel/{reservationId}")
    String cancelStudyRoom(@PathVariable String reservationId);

    @GetMapping(ApiRouterConstant.ROOM_RESERVATION_URL_PREFIX + "/query")
    String getReservations(ReservationByUserReqDto reservationByUserReqDto);

    @GetMapping(ApiRouterConstant.ROOM_SEAT_URL_PREFIX + "/{roomId}/seats")
    String getSeatsByRoomId(@PathVariable String roomId);

    @GetMapping(ApiRouterConstant.ROOM_URL_PREFIX + "/all")
    String getAllStudyRooms();

    @PostMapping(ApiRouterConstant.ROOM_URL_PREFIX + "/query_by_condition")
    String searchStudyRooms(@RequestBody RoomQueryByConditionReqDto roomQueryByConditionReqDto);

    @GetMapping(ApiRouterConstant.TIME_URL_PREFIX + "/all")
    String getAllTime();

    @GetMapping(ApiRouterConstant.ROOM_URL_PREFIX + "/{roomId}/time_slots")
    String getTimeSlotsByRoomId(@PathVariable String roomId);
    @GetMapping(ApiRouterConstant.ROOM_URL_PREFIX + "/{roomId}/date")
    String getDateByRoomId(@PathVariable String roomId);

    @PostMapping(ApiRouterConstant.ROOM_URL_PREFIX + "/view_seats")
    String getSeatInfoByDateAndTime(SeatViewReqDto seatViewReqDto);

    @PostMapping(ApiRouterConstant.ROOM_URL_PREFIX + "/getAllRoomsByAdmin")
    String getAllRoomsByAdmin(GetAllRoomByAdminReqDto getAllRoomByAdminReqDto);

    @PostMapping(ApiRouterConstant.ROOM_RESERVATION_URL_PREFIX + "/all_reservations")
    String getAllReservations(GetAllReservationByAdminReqDto getAllReservations);

    @PutMapping(ApiRouterConstant.ROOM_RESERVATION_URL_PREFIX + "/update_reservation")
    String updateReservation(ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto);


}

