package com.ewing.controller;

import Page.PageRespDto;
import Result.ResultPage;
import cn.hutool.log.Log;
import com.ewing.annotation.MyLog;
import com.ewing.annotation.RequiresPermission;
import com.ewing.domain.dto.*;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.dto.resp.LogRespDto;
import com.ewing.domain.dto.resp.LostFoundByAdminDto;
import com.ewing.domain.entity.LogTable;
import com.ewing.domain.entity.UserTable;
import com.ewing.service.*;
import constant.ApiRouterConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-28-13:32
 * @Description:
 */
@RestController
@Tag(name = "管理员模块", description = "管理员模块相关接口")
@RequestMapping(ApiRouterConstant.ADMIN_URL_PREFIX)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final LogTableService logTableService;

    private final AnnouncementTableService announcementTableService;

    private final LostFoundTableService lostFoundTableService;

    @Operation(summary = "管理员解封用户")
    @PostMapping("/unblock")
    @MyLog(title = "管理员模块", content = "管理员解封用户")
    @RequiresPermission("MANAGE_USERS")
    public ResultPage<Void> unblock(@RequestBody @Validated UserDisableReqDto userDisableReqDto) {
        return adminService.unblock(userDisableReqDto);
    }

    @Operation(summary = "管理员删除用户")
    @PostMapping("/delete")
    @MyLog(title = "管理员模块", content = "管理员删除用户")
    @RequiresPermission("MANAGE_USERS")
    public ResultPage<Void> delete(@RequestBody @Validated UserDeleteReqDto userDeleteReqDto) {
        return adminService.delete(userDeleteReqDto);
    }

    @Operation(summary = "管理员禁用用户")
    @PostMapping("/disable")
    @MyLog(title = "管理员模块", content = "管理员禁用用户")
    @RequiresPermission("MANAGE_USERS")
    public ResultPage<Void> disable(@RequestBody @Validated UserDisableReqDto userDisableReqDto) {
        return adminService.disable(userDisableReqDto);
    }


    @Operation(summary = "管理员恢复删除用户")
    @PostMapping("/undelete")
    @MyLog(title = "管理员模块", content = "管理员恢复删除用户")
    @RequiresPermission("MANAGE_USERS")
    public ResultPage<Void> undelete(@RequestBody @Validated UserDeleteReqDto userDeleteReqDto) {
        return adminService.undelete(userDeleteReqDto);
    }

    @Operation(summary = "管理员新建自习室")
    @PostMapping("/create_room")
    @MyLog(title = "管理员模块", content = "管理员新建自习室")
    public ResultPage<Void> someMethodToCreateStudyRoom(@RequestBody RoomCreateReqDto roomDTO) {
        return adminService.someMethodToCreateStudyRoom(roomDTO);
    }

    @Operation(summary = "管理员更新自习室")
    @PutMapping("/update_room")
    @MyLog(title = "管理员模块", content = "管理员更新自习室")
    public ResultPage<Void> someMethodToUpdateStudyRoom(@RequestBody RoomUpdateReqDto roomDTO) {
        return adminService.someMethodToUpdateStudyRoom(roomDTO);
    }

    @Operation(summary = "管理员删除自习室")
    @DeleteMapping("/delete_room/{roomId}")
    @MyLog(title = "管理员模块", content = "管理员更新自习室")
    public ResultPage<Void> someMethodToDeleteStudyRoom(@PathVariable String roomId) {
        return adminService.someMethodToDeleteStudyRoom(roomId);
    }

//    @Operation(summary = "获取所有用户信息")
//    @MyLog(title = "管理员模块", content = "获取所有用户信息")
//    @GetMapping("/all_users")
//    @RequiresPermission("MANAGE_USERS")
//    public ResultPage<List<UserTable>> getAllUsers() {
//        return adminService.getAllUsers();
//    }

//    @Operation(summary = "获取所有权限信息")
//    @MyLog(title = "管理员模块", content = "获取所有权限信息")
//    @GetMapping("/all_permissions")
//    public ResultPage<List<String>> getAllPermissions() {
//        return adminService.getAllPermissions();
//    }

//    @Operation(summary = "新增权限")
//    @MyLog(title = "管理员模块", content = "新增权限")
//    @PostMapping("/add_permission")
//    @RequiresPermission("MANAGE_PERMISSIONS")
//    public ResultPage<Void> addPermission(@RequestBody String permission) {
//        return adminService.addPermission(permission);
//    }
//
//    @Operation(summary = "删除权限")
//    @MyLog(title = "管理员模块", content = "删除权限")
//    @DeleteMapping("/delete_permission/{permission}")
//    @RequiresPermission("MANAGE_PERMISSIONS")
//    public ResultPage<Void> deletePermission(@PathVariable String permission) {
//        return adminService.deletePermission(permission);
//    }
//
//    @Operation(summary = "用户分配权限")
//    @MyLog(title = "管理员模块", content = "用户分配权限")
//    @PostMapping("/assign_permission")
//    @RequiresPermission("MANAGE_PERMISSIONS")
//    public ResultPage<Void> assignPermission(@RequestBody UserTable userTable) {
//        return adminService.assignPermission(userTable);
//    }
//
//    @Operation(summary = "根据自习室ID获取所有预约信息")
//    @GetMapping("/query_reservation/{roomId}")
//    public ResultPage<Object> someMethodToQueryStudyRoom(@PathVariable String roomId) {
//        return adminService.someMethodToQueryStudyRoom(roomId);
//    }
//
//    @Operation(summary = "根据自习室ID座位ID用户ID编辑用户预约信息")
//    @PutMapping("/edit_reservation/{roomId}/{seatId}/{userId}")
//    public ResultPage<Object> someMethodToEditStudyRoom(@PathVariable String roomId, @PathVariable String seatId, @PathVariable String userId, @RequestBody Object object) {
//        return adminService.someMethodToEditStudyRoom(roomId, seatId, userId, object);
//    }
//
//    @Operation(summary = "根据自习室ID座位ID编辑座位信息")
//    @PutMapping("/edit_seat/{roomId}/{seatId}")
//    public ResultPage<Object> someMethodToEditSeat(@PathVariable String roomId, @PathVariable String seatId, @RequestBody Object object) {
//        return adminService.someMethodToEditSeat(roomId, seatId, object);
//    }

    @Operation(summary = "管理员获取所有日志信息")
    @PostMapping("/all_logs")
    public ResultPage<PageRespDto<LogRespDto>> getAllLogs(@RequestBody @Validated LogReqDto logReqDto) {
        return logTableService.getAllLogs(logReqDto);
    }

    @Operation(summary = "管理员获取所有公告信息")
    @PostMapping("/all_announcements")
    public ResultPage<PageRespDto<AnnouncementByAdminDto>> getAllAnnouncements(@RequestBody @Validated AnnouncementReqDto announcementReqDto) {
        return announcementTableService.getAnnouncementsByAdmin(announcementReqDto);
    }

    @Operation(summary = "管理员获取所有失物招领信息")
    @PostMapping("/all_lost_founds")
    public ResultPage<PageRespDto<LostFoundByAdminDto>> getAllLoststByAdmin(@RequestBody @Validated LostFoundAllByAdminReqDto lostFoundAllByAdminReqDto) {
        return lostFoundTableService.getAllLoststByAdmin(lostFoundAllByAdminReqDto);
    }

    @Operation(summary = "管理员管理失物招领信息")
    @PostMapping("/manager_lost_found")
    public ResultPage<Void> managerByAdmin(@RequestBody @Validated LostFoundAdminReqDto lostFoundAdminReqDto) {
        return lostFoundTableService.managerByAdmin(lostFoundAdminReqDto);
    }

    @Operation(summary = "管理员管理所有用户")
    @PostMapping("/manager_all_users")
    public ResultPage<PageRespDto<UserInfoByAdminDto>> managerAllUsers(@RequestBody @Validated UserInfoAllByAdminReqDto userInfoAllByAdminReqDto) {
        return adminService.managerAllUsers(userInfoAllByAdminReqDto);
    }

    @Operation(summary = "管理员更新用户")
    @PostMapping("/update_user_by_admin")
    @MyLog(title = "管理员模块", content = "管理员更新用户")
    public ResultPage<Void> UpdateUserByAdmin(@RequestBody @Validated UserUpdateByAdminReqDto userUpdateByAdminReqDto) {
        return adminService.UpdateUserByAdmin(userUpdateByAdminReqDto);
    }

    @Operation(summary = "管理员所有角色和权限信息")
    @PostMapping("/get_all_role_permissions")
    @MyLog(title = "管理员模块", content = "管理员所有角色和权限信息")
    public ResultPage<PageRespDto<RolesPermissionByAdminDto>> getAllRolePermissions(@RequestBody @Validated RolesPermissionsAllByAdminReqDto rolesPermissionsAllByAdminReqDto) {
        return adminService.getAllRolePermissions(rolesPermissionsAllByAdminReqDto);
    }


    @Operation(summary = "管理员新增角色")
    @PostMapping("/create_role")
    @MyLog(title = "管理员模块", content = "管理员新增角色")
    public ResultPage<Void> createRolesByAdmin(@RequestBody @Validated RolesCreateByAdminReqDto rolesCreateByAdminReqDto) {
        return adminService.createRolesByAdmin(rolesCreateByAdminReqDto);
    }

    @Operation(summary = "管理员新增权限")
    @PostMapping("/create_permission")
    public ResultPage<Void> createPermissionByAdmin(@RequestBody @Validated PermissionsCreateByAdminReqDto permissionCreateByAdminReqDto) {
        return adminService.createPermissionByAdmin(permissionCreateByAdminReqDto);
    }

    @Operation(summary = "管理员为角色增加或删除权限")
    @PutMapping("/update_role")
    @MyLog(title = "管理员模块", content = "管理员为角色增加或删除权限")
    public ResultPage<Void> updateRolePermissionsByAdmin(@RequestBody @Validated UpdateRolesByAdminReqDto updateRolesByAdminReqDto) {
        return adminService.updateRolePermissionsByAdmin(updateRolesByAdminReqDto);
    }

    @Operation(summary = "管理员获取所有角色")
    @GetMapping("/all_roles")
    public ResultPage<List<RolesAllByAdminDto>> getAllRolesByAdmin() {
        return adminService.getAllRolesByAdmin();
    }

    @Operation(summary = "管理员获取所有权限")
    @GetMapping("/all_permissions")
    public ResultPage<List<PermissionAllByAdminDto>> getAllPermissionsByAdmin() {
        return adminService.getAllPermissionsByAdmin();
    }

    @Operation(summary = "管理员获取所有自习室")
    @PostMapping("/all_rooms")
    public ResultPage<Object> getAllRooms(@RequestBody @Validated GetAllRoomByAdminReqDto getAllRoomByAdminReqDto) {
        return adminService.getAllRooms(getAllRoomByAdminReqDto);
    }

    @Operation(summary = "管理员获取所有预约信息")
    @PostMapping("/all_reservations")
    @MyLog(title = "管理员模块", content = "管理员获取所有预约信息")
    public ResultPage<Object> someMethodToGetAllReservations(@RequestBody @Validated GetAllReservationByAdminReqDto getAllReservationByAdminReqDto) {
        return adminService.someMethodToGetAllReservations(getAllReservationByAdminReqDto);
    }

    @Operation(summary = "管理员更新预约信息")
    @PutMapping("/update_reservation")
    @MyLog(title = "管理员模块", content = "管理员更新预约信息")
    public ResultPage<Void> someMethodToUpdateReservation(@RequestBody ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto) {
        return adminService.someMethodToUpdateReservation(reservationUpdateByAdminReqDto);
    }

    @Operation(summary = "管理员删除留言接口")
    @MyLog(title = "管理员模块", content = "管理员删除留言")
    @DeleteMapping("/delete_by_admin/{messageId}")
    public ResultPage<Void> deleteMessageByAdmin(@PathVariable("messageId") String messageId) {
        return adminService.deleteMessageByAdmin(messageId);
    }

    @Operation(summary = "管理员获取所有留言接口")
    @MyLog(title = "管理员模块", content = "管理员获取所有留言")
    @PostMapping("/admin/all")
    public ResultPage<PageRespDto<MessageByAdminDto>> getAllMessagesByAdmin(@RequestBody @Validated MessageByAdminReqDto messageByAdminReqDto) {
        return adminService.getAllMessagesByAdmin(messageByAdminReqDto);
    }

    @Operation(summary = "管理员获取所有预约信息接口")
    @MyLog(title = "管理员模块", content = "管理员获取所有预约信息")
    @PostMapping("/admin/all_reservations")
    public ResultPage<Object> someMethodToGetAllReservationsByAdmin(@RequestBody @Validated GetAllReservationByAdminReqDto getAllReservationByAdminReqDto) {
        return adminService.someMethodToGetAllReservationsByAdmin(getAllReservationByAdminReqDto);
    }

    @Operation(summary = "管理员获更新预约信息接口")
    @MyLog(title = "管理员模块", content = "管理员更新预约信息")
    @PutMapping("/admin/update_reservation")
    public ResultPage<Void> someMethodToUpdateReservationByAdmin(@RequestBody ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto) {
        return adminService.someMethodToUpdateReservationByAdmin(reservationUpdateByAdminReqDto);
    }
}