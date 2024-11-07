package com.ewing.mapper;

import com.ewing.domain.entity.SeatTimeTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author ewing
* @description 针对表【seat_time_table(座位时间段表)】的数据库操作Mapper
* @createDate 2024-11-02 21:42:34
* @Entity com.ewing.domain.entity.SeatTimeTable
*/
public interface SeatTimeTableMapper extends BaseMapper<SeatTimeTable> {
    void deleteByRoomId(@Param("roomId") String roomId);

    void updateStatusByRoomId(@Param("roomId") String roomId, @Param("status") String status);

}




