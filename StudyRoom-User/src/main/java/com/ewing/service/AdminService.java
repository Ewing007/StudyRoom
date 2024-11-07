package com.ewing.service;

import Page.PageRespDto;
import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.dto.*;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.entity.UserTable;

import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-28-13:37
 * @Description:
 */
public interface AdminService extends IService<UserTable> {
    ResultPage<Void> disable(UserDisableReqDto userDisableReqDto);

    ResultPage<Void> unblock(UserDisableReqDto userDisableReqDto);

    ResultPage<Void> delete(UserDeleteReqDto userDeleteReqDto);

    ResultPage<Void> undelete(UserDeleteReqDto userDeleteReqDto);

    ResultPage<Void> someMethodToCreateStudyRoom(RoomCreateReqDto roomDTO);

    ResultPage<Void> someMethodToUpdateStudyRoom( RoomUpdateReqDto roomDTO);

    ResultPage<Void> someMethodToDeleteStudyRoom(String roomId);

    ResultPage<List<UserTable>> getAllUsers();

    ResultPage<List<String>> getAllPermissions();

    ResultPage<Void> addPermission(String permission);

    ResultPage<Void> deletePermission(String permission);

    ResultPage<Void> assignPermission(UserTable userTable);

    ResultPage<Object> someMethodToQueryStudyRoom(String roomId);

    ResultPage<Object> someMethodToEditStudyRoom(String roomId, String seatId, String userId, Object object);

    ResultPage<Object> someMethodToEditSeat(String roomId, String seatId, Object object);

    ResultPage<PageRespDto<UserInfoByAdminDto>> managerAllUsers(UserInfoAllByAdminReqDto userInfoAllByAdminReqDto);

    ResultPage<Void> UpdateUserByAdmin(UserUpdateByAdminReqDto userUpdateByAdminReqDto);

    ResultPage<PageRespDto<RolesPermissionByAdminDto>> getAllRolePermissions(RolesPermissionsAllByAdminReqDto rolesPermissionsAllByAdminReqDto);

    ResultPage<Void> createRolesByAdmin(RolesCreateByAdminReqDto rolesCreateByAdminReqDto);

    ResultPage<Void> createPermissionByAdmin(PermissionsCreateByAdminReqDto permissionCreateByAdminReqDto);

    ResultPage<Void> updateRolePermissionsByAdmin(UpdateRolesByAdminReqDto updateRolesByAdminReqDto);

    ResultPage<List<RolesAllByAdminDto>> getAllRolesByAdmin();

    ResultPage<List<PermissionAllByAdminDto>> getAllPermissionsByAdmin();

    ResultPage<Object> getAllRooms(GetAllRoomByAdminReqDto getAllRoomByAdminReqDto);

    ResultPage<Object> someMethodToGetAllReservations(GetAllReservationByAdminReqDto getAllReservationByAdminReqDto);

    ResultPage<Void> someMethodToUpdateReservation(ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto);

    ResultPage<Void> deleteMessageByAdmin(String messageId);

    ResultPage<PageRespDto<MessageByAdminDto>> getAllMessagesByAdmin(MessageByAdminReqDto messageByAdminReqDto);

    ResultPage<Object> someMethodToGetAllReservationsByAdmin(GetAllReservationByAdminReqDto getAllReservationByAdminReqDto);

    ResultPage<Void> someMethodToUpdateReservationByAdmin(ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto);
}
