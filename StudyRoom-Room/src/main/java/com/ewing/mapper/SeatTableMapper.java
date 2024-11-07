package com.ewing.mapper;

import com.ewing.domain.entity.SeatTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author ewing
* @description 针对表【seat_table(座位表)】的数据库操作Mapper
* @createDate 2024-10-15 20:30:11
* @Entity com.ewing.domain.entity.SeatTable
*/
@Mapper
public interface SeatTableMapper extends BaseMapper<SeatTable> {

    void deleteByRoomId(@Param("roomId") String roomId);

    void updateStatusByRoomId(@Param("roomId") String roomId, @Param("status") String status);
}




