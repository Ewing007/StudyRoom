package com.ewing.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ewing.domain.entity.SeatTable;
import com.ewing.domain.entity.SeatTimeTable;
import com.ewing.mapper.SeatTableMapper;
import com.ewing.mapper.SeatTimeTableMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-11-05-15:36
 * @Description: 定时任务 - 删除七天前的数据
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteOldDataJobHandler {

    private final SeatTimeTableMapper seatTimeTableMapper;
    private final SeatTableMapper seatTableMapper;

    @XxlJob("deleteOldDataJobHandler")
    public ReturnT<String> execute(String param) {
        log.info("开始定时任务：删除七天前的数据...");

        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 计算七天前的日期
        LocalDate cutoffDate = today.minusDays(7);

        // 删除 seat_time_table 表中七天前的数据
        int deletedCountSeatTimeTable = seatTimeTableMapper.delete(new QueryWrapper<SeatTimeTable>()
                .lt("date", cutoffDate));
        log.info("删除 seat_time_table 旧数据成功，共删除 {} 条记录", deletedCountSeatTimeTable);

        // 获取七天前的座位ID
        List<String> seatIdsToDelete = seatTimeTableMapper.selectList(new QueryWrapper<SeatTimeTable>()
                        .lt("date", cutoffDate))
                .stream()
                .map(SeatTimeTable::getSeatId)
                .distinct()
                .toList();

        // 删除 seat_table 表中七天前的数据
        int deletedCountSeatTable = seatTableMapper.delete(new QueryWrapper<SeatTable>()
                .in("seat_id", seatIdsToDelete));
        log.info("删除 seat_table 旧数据成功，共删除 {} 条记录", deletedCountSeatTable);

        return ReturnT.SUCCESS;
    }
}
