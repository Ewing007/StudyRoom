package com.ewing.controller;

import Result.ResultPage;
import com.ewing.annotation.MyLog;
import com.ewing.annotation.RequiresPermission;

import com.ewing.domain.dto.req.*;
import com.ewing.domain.dto.resp.UserRegisterRespDto;
import com.ewing.domain.dto.resp.UserLoginRespDto;
import com.ewing.domain.dto.resp.UserUpdateRespDto;
import com.ewing.service.UserTableService;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import Exception.BusinessException;

import java.util.Date;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-08-11:05
 * @Description:
 */
@RestController
@Tag(name = "用户管理模块", description = "用户登录注册接口")
@RequestMapping(ApiRouterConstant.USER_URL_PREFIX)
@RequiredArgsConstructor
public class UserController {

    private final UserTableService userTableService;

    @Operation(summary = "接口测试")
    @GetMapping("/test")
    @MyLog(title = "用户模块", content = "测试用户模块")
    public ResultPage<String> test() {
        return ResultPage.SUCCESS("test admin");
    }

    @Operation(summary = "注册接口")
    @MyLog(title = "用户模块", content = "注册新用户")
    @PostMapping("/register")
    public ResultPage<UserRegisterRespDto> register(@Validated @RequestBody UserRegisterReqDto userRegisterReqDto) throws BusinessException {
        return userTableService.register(userRegisterReqDto);
    }

    @Operation(summary = "登录接口")
    @PostMapping("/login")
    @MyLog(title = "用户模块", content = "用户登录系统")
    public ResultPage<UserLoginRespDto> login(@Validated @RequestBody UserLoginReqDto userLoginReqDto) throws BusinessException {
        return userTableService.login(userLoginReqDto);
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    @MyLog(title = "用户模块", content = "用户退出系统")
    public ResultPage<Void> logout() {

        return userTableService.logout();
    }

    @Operation(summary = "更新用户信息")
    @PostMapping("/update")
    @MyLog(title = "用户模块", content = "更新用户信息")
    public ResultPage<UserUpdateRespDto> update(@Validated @RequestBody UserUpdateReqDto userUpdateReqDto) throws BusinessException {
        return userTableService.update(userUpdateReqDto);
    }
//
//    @Operation(summary = "禁用用户")
//    @PostMapping("/disable")
//    @MyLog(title = "用户模块", content = "禁用用户")
//    @RequiresPermission("MANAGE_USERS")
//    public ResultPage<Void> disable(@RequestBody @Validated UserDisableReqDto userDisableReqDto) {
//        return userTableService.disable(userDisableReqDto);
//    }
//    @Operation(summary = "解封用户")
//    @PostMapping("/unblock")
//    @MyLog(title = "用户模块", content = "解封用户")
//    @RequiresPermission("MANAGE_USERS")
//    public ResultPage<Void> unblock(@RequestBody @Validated UserDisableReqDto userDisableReqDto) {
//        return userTableService.unblock(userDisableReqDto);
//    }
//
//    @Operation(summary = "删除用户")
//    @PostMapping("/delete")
//    @MyLog(title = "用户模块", content = "删除用户")
//    @RequiresPermission("MANAGE_USERS")
//    public ResultPage<Void> delete(@RequestBody @Validated UserDeleteReqDto userDeleteReqDto) {
//        return userTableService.delete(userDeleteReqDto);
//    }
//
//
//    @Operation(summary = "恢复删除用户")
//    @PostMapping("/undelete")
//    @MyLog(title = "用户模块", content = "恢复删除用户")
//    @RequiresPermission("MANAGE_USERS")
//    public ResultPage<Void> undelete(@RequestBody @Validated UserDeleteReqDto userDeleteReqDto) {
//        return userTableService.undelete(userDeleteReqDto);
//    }

//    @Operation(summary = "管理员新建自习室")
//    @PostMapping("/create_room")
//    @MyLog(title = "用户模块", content = "管理员新建自习室")
//    public ResultPage<Void> someMethodToCreateStudyRoom(@RequestBody RoomCreateReqDto roomDTO){
//        return userTableService.someMethodToCreateStudyRoom(roomDTO);
//    }
//
//    @Operation(summary = "管理员更新自习室")
//    @PutMapping("/update_room/{roomId}")
//    @MyLog(title = "用户模块", content = "管理员更新自习室")
//    public ResultPage<Void> someMethodToUpdateStudyRoom(@PathVariable String  roomId, @RequestBody RoomUpdateReqDto roomDTO){
//        return userTableService.someMethodToUpdateStudyRoom(roomId,roomDTO);
//    }

    @Operation(summary = "用户预约自习室")
    @PostMapping("/book")
    @MyLog(title = "用户模块", content = "用户预约自习室")
    public ResultPage<String> someMethodToBookStudyRoom(@RequestBody BookRoomReqDto bookRoomReqDto){
        return userTableService.someMethodToBookStudyRoom(bookRoomReqDto);
    }

    @Operation(summary = "用户取消预约自习室")
    @PostMapping("/cancel/{reservationId}")
    @MyLog(title = "用户模块", content = "用户取消预约自习室")
    public ResultPage<String> someMethodToCancelStudyRoom(@PathVariable String reservationId){
        return userTableService.someMethodToCancelStudyRoom(reservationId);
    }

    @Operation(summary = "用户查询预约记录")
    @MyLog(title = "用户模块", content = "用户查询预约记录")
    @PostMapping("/query_reservation")
    public ResultPage<Object> someMethodToQueryReservation(@RequestBody @Validated ReservationByUserReqDto reservationByUserReqDto) {
        return userTableService.someMethodToQueryReservation(reservationByUserReqDto);
    }

    @Operation(summary = "根据roomId获取座位信息获取所有座位信息")
    @MyLog(title = "用户模块", content = "根据roomId获取座位信息获取所有座位信息")
    @GetMapping("/{roomId}/seats")
    public ResultPage<Object> someMethodToQuerySeats(@PathVariable String roomId){
        return userTableService.someMethodToQuerySeats(roomId);
    }

    @Operation(summary = "获取所有自习室信息,首页展示")
    @GetMapping("/rooms")
    public ResultPage<Object> someMethodToGetAllStudyRooms(){
        return userTableService.someMethodToGetAllStudyRooms();
    }

    @Operation(summary = "根据帅选条件获取自习室信息")
    @PostMapping("/query_by_condition")
    public ResultPage<Object> someMethodToQueryStudyRoomByCondition(@RequestBody RoomQueryByConditionReqDto queryConditionReqDto){
        return userTableService.someMethodToQueryStudyRoomByCondition(queryConditionReqDto);
    }

    @Operation(summary = "获取所有时间字段")
    @GetMapping("/get_all_time")
    public ResultPage<Object> someMethodToGetAllTime(){
        return userTableService.someMethodToGetAllTime();
    }

    @GetMapping("/{roomId}/time_slots")
    @Operation(summary = "获取某个自习室所有可预约时间字段")
    public ResultPage<Object> getTimeSlotsByRoomId(@PathVariable String roomId) {
        return userTableService.someMethodToGetTimeSlotsByRoomId(roomId);
    }

    @GetMapping("/{roomId}/date")
    @Operation(summary = "获取某个自习室所有可预约日期")
    public ResultPage<Object> getDateByRoomId(@PathVariable String roomId) {
        return userTableService.someMethodToGetDateByRoomId(roomId);
    }

    @PostMapping("/view_seats")
    @Operation(summary = "可预约日期时间段内的座位信息")
    public ResultPage<Object> methodToGetSeatInfoByDateAndTime(@RequestBody @Validated SeatViewReqDto seatViewReqDto) {
        return userTableService.methodToGetSeatInfoByDateAndTime(seatViewReqDto);
    }

}
