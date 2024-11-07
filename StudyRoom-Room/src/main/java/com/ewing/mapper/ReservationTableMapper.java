package com.ewing.mapper;

import com.ewing.domain.entity.ReservationTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
* @author ewing
* @description 针对表【reservation_table(自习室座位预约表)】的数据库操作Mapper
* @createDate 2024-10-15 20:30:49
* @Entity com.ewing.domain.entity.ReservationTable
*/
@Mapper
public interface ReservationTableMapper extends BaseMapper<ReservationTable> {
    /**
     * 查询所有已确认且未完成的预约，用于定时任务检查签到和违约
     */
    List<ReservationTable> selectActiveReservations();

    /**
     * 更新预约状态
     */
    void updateReservationStatus(@Param("id") Long id, @Param("status") String status);
}




