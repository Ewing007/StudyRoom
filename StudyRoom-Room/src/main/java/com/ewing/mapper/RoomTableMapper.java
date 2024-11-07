package com.ewing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.ewing.domain.dto.SeatDto;
import com.ewing.domain.entity.RoomTable;
import com.ewing.domain.entity.SeatTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
* @author ewing
* @description 针对表【room_table(自习室表)】的数据库操作Mapper
* @createDate 2024-10-13 14:05:02
* @Entity generator.domain.RoomTable
*/
@Mapper
public interface RoomTableMapper extends BaseMapper<RoomTable> {

    List<RoomTable> selectAllActiveRooms();

     void deleteRoomAndRelatedRecords(String roomId);

    List<RoomTable> queryByCondition(Map<String, Object> params);

    IPage<RoomTable> queryByConditionPage(IPage<RoomTable> page, Map<String, Object> params);

    List<String> getSlotIdsByRoomId(String roomId);
    Set<Date> getDatesByRoomId(String roomId);

    List<SeatTable> getSeatInfoByDateAndTime(@Param("roomId") String roomId, @Param("date") LocalDate date, @Param("slotId") String slotId);

}




