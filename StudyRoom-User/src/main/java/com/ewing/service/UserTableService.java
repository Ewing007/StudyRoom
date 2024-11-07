package com.ewing.service;

import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.ewing.domain.dto.UserDto;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.dto.resp.UserRegisterRespDto;
import com.ewing.domain.dto.resp.UserLoginRespDto;
import com.ewing.domain.dto.resp.UserUpdateRespDto;
import com.ewing.domain.entity.UserTable;
import Exception.BusinessException;

import java.util.Date;
import java.util.List;

/**
* @author ewing
* @description 针对表【user_table(用户表)】的数据库操作Service
* @createDate 2024-10-09 21:12:24
*/
public interface UserTableService extends IService<UserTable> {

    ResultPage<UserLoginRespDto> login(UserLoginReqDto userLoginReqDto) throws BusinessException;

    ResultPage<UserRegisterRespDto> register(UserRegisterReqDto userRegisterReqDto) throws BusinessException;

    ResultPage<Void> logout();


    ResultPage<UserUpdateRespDto> update(UserUpdateReqDto userUpdateReqDto) throws BusinessException;

    UserDto getUserDtoById(String userId) throws BusinessException;

    ResultPage<Void> disable(UserDisableReqDto userDisableReqDto);

    ResultPage<Void> unblock(UserDisableReqDto userDisableReqDto);

    ResultPage<Void> delete(UserDeleteReqDto userDeleteReqDto);

    ResultPage<Void> undelete(UserDeleteReqDto userDeleteReqDto);

    ResultPage<Void> someMethodToCreateStudyRoom(RoomCreateReqDto roomDTO);

    ResultPage<Void> someMethodToUpdateStudyRoom(String roomId, RoomUpdateReqDto roomDTO);

    ResultPage<String> someMethodToBookStudyRoom(BookRoomReqDto bookRoomReqDto);

    ResultPage<String> someMethodToCancelStudyRoom(String reservationId);

    ResultPage<Object> someMethodToQueryReservation(ReservationByUserReqDto reservationByUserReqDto);

    ResultPage<Object> someMethodToQuerySeats(String roomId);

    ResultPage<Object> someMethodToGetAllStudyRooms();

    ResultPage<Void> updateUserAvatar( String avatarUrl) throws BusinessException;

    ResultPage<Object> someMethodToQueryStudyRoomByCondition(RoomQueryByConditionReqDto queryConditionReqDto);

    ResultPage<Object> someMethodToGetAllTime();

    ResultPage<Object> someMethodToGetTimeSlotsByRoomId(String roomId);

    ResultPage<Object> someMethodToGetDateByRoomId(String roomId);

    ResultPage<Object> methodToGetSeatInfoByDateAndTime(SeatViewReqDto seatViewReqDto);
}
