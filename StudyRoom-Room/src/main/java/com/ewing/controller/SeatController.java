package com.ewing.controller;

import Result.ResultPage;
import com.ewing.annotation.MyLog;
import com.ewing.domain.dto.SeatDto;
import com.ewing.domain.entity.SeatTable;
import com.ewing.service.SeatTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-27-18:02
 * @Description:
 */
@RestController
@Tag(name = "自习室座位管理模块", description = "自习室座位管理接口")
@RequestMapping(ApiRouterConstant.ROOM_SEAT_URL_PREFIX)
@RequiredArgsConstructor
public class SeatController {
    private final SeatTableService seatService;

//    @GetMapping("/{roomId}/seats")
//    @Operation(summary = "根据房间号获取座位信息", description = "根据房间号获取座位信息")
//    @MyLog(title = "自习室座位管理模块模块", content = "根据房间号获取座位信息")
//    public ResultPage<List<SeatDto>> getSeatsByRoomId(@PathVariable String roomId) {
//        return seatService.getSeatsByRoomId(roomId);
//    }
}
