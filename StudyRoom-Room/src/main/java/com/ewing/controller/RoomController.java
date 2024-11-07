package com.ewing.controller;

import Page.PageRespDto;
import Result.ResultPage;
import com.ewing.annotation.MyLog;
import com.ewing.annotation.RequiresPermission;
import com.ewing.domain.dto.RoomByAminDto;
import com.ewing.domain.dto.RoomDto;
import com.ewing.domain.dto.SeatDto;
import com.ewing.domain.dto.req.*;
import com.ewing.service.RoomTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import Exception.BusinessException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Author: Ewing
 * @Date: 2024-10-13-13:58
 * @Description:
 */
@RestController
@Tag(name = "自习室管理模块", description = "自习室管理接口")
@RequestMapping(ApiRouterConstant.ROOM_URL_PREFIX)
@RequiredArgsConstructor
public class RoomController {

    private final RoomTableService roomTableService;



    /**
     * 创建自习室
     */
    @PostMapping("/create")
    @RequiresPermission("MANAGE_STUDY_ROOM")
    @MyLog(title = "自习室模块", content = "创建自习室")
    public ResultPage<Void> createStudyRoom(@RequestBody @Validated RoomCreateReqDto room) {
        return roomTableService.createStudyRoom(room);
    }

    @PostMapping("/getAllRoomsByAdmin")
    @RequiresPermission("MANAGE_STUDY_ROOM")
    @MyLog(title = "自习室模块", content = "管理员查看所有自习室状态")
    public ResultPage<PageRespDto<RoomByAminDto>> getAllRoomsByAdmin(@RequestBody @Validated GetAllRoomByAdminReqDto getAllRoomByAdminReqDto) {
        return roomTableService.getAllRoomsByAdmin(getAllRoomByAdminReqDto);
    }

    /**
     * 更新自习室信息
     */
    @PutMapping("/updateRoom")
    @RequiresPermission("MANAGE_STUDY_ROOM")
    @MyLog(title = "自习室模块", content = "更新自习室")
    public ResultPage<Void> updateStudyRoom(@RequestBody RoomUpdateReqDto roomTable) {
        return roomTableService.updateStudyRoom(roomTable);
    }

    /**
     * 删除自习室
     */
    @DeleteMapping("/{roomId}")
//    @RequiresPermission("MANAGE_STUDY_ROOM")
//    @MyLog(title = "自习室模块", content = "删除自习室")
    public ResultPage<Void> deleteStudyRoom(@PathVariable String roomId) throws BusinessException {
        return roomTableService.deleteStudyRoom(roomId);
    }

    /**
     * 获取所有自习室
     */
    @GetMapping("/all")
//    @MyLog(title = "自习室模块", content = "获取所有自习室")
//    @RequiresPermission("view_studyroom")
    public ResultPage<List<RoomDto>> getAllStudyRooms() {
        return roomTableService.getAllStudyRooms();
    }


    @PostMapping("/query_by_condition")
//    @RequiresPermission("view_studyroom")
    @MyLog(title = "自习室模块", content = "按条件搜索自习室")
    public ResultPage<PageRespDto<RoomDto>> searchStudyRooms(@RequestBody RoomQueryByConditionReqDto roomQueryByConditionReqDto) {
        return roomTableService.queryByCondition(roomQueryByConditionReqDto);
    }

    @GetMapping("/{roomId}/time_slots")
    @MyLog(title = "自习室模块", content = "获取自习室的可用时间段")
    public ResultPage<List<String>> getTimeSlotsByRoomId(@PathVariable String roomId) {
        return roomTableService.getTimeSlotsByRoomId(roomId);
        }

    @GetMapping("/{roomId}/date")
    @MyLog(title = "自习室模块", content = "获取自习室的可用日期")
    public ResultPage<Set<LocalDate>> getDateByRoomId(@PathVariable String roomId) {
        return roomTableService.getDateByRoomId(roomId);
    }

    @PostMapping("/view_seats")
    @MyLog(title = "自习室模块", content = "获取自习室具体日期和时间段内的所有座位详情")
    public ResultPage<List<SeatDto>> getSeatInfoByDateAndTime(@RequestBody @Validated SeatViewReqDto seatViewReqDto) {
        return roomTableService.getSeatInfoByDateAndTime(seatViewReqDto);
    }
}
