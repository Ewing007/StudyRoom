package com.ewing.service;

import Page.PageRespDto;
import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.dto.RoomByAminDto;
import com.ewing.domain.dto.RoomDto;
import com.ewing.domain.dto.SeatDto;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.entity.RoomTable;
import Exception.BusinessException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
* @author ewing
* @description 针对表【room_table(自习室表)】的数据库操作Service
* @createDate 2024-10-13 14:05:02
*/
public interface RoomTableService extends IService<RoomTable> {


    ResultPage<Void> createStudyRoom(RoomCreateReqDto room);

    ResultPage<Void> updateStudyRoom(RoomUpdateReqDto roomUpdateReqDto);

    ResultPage<Void> deleteStudyRoom(String roomId) throws BusinessException;

    ResultPage<List<RoomDto>> getAllStudyRooms();

    ResultPage<PageRespDto<RoomDto>> queryByCondition(RoomQueryByConditionReqDto roomQueryByConditionReqDto);

    ResultPage<List<String>> getTimeSlotsByRoomId(String roomId);

    ResultPage<Set<LocalDate>> getDateByRoomId(String roomId);

    ResultPage<List<SeatDto>> getSeatInfoByDateAndTime(SeatViewReqDto seatViewReqDto);


    ResultPage<PageRespDto<RoomByAminDto>> getAllRoomsByAdmin(GetAllRoomByAdminReqDto getAllRoomByAdminReqDto);
}
