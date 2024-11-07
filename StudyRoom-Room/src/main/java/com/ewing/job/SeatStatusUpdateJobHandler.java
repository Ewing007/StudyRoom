package com.ewing.job;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ewing.domain.entity.SeatTable;
import com.ewing.domain.entity.SeatTimeTable;
import com.ewing.mapper.SeatTableMapper;
import com.ewing.mapper.SeatTimeTableMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-11-05-15:36
 * @Description:
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SeatStatusUpdateJobHandler {


    private final SeatTimeTableMapper seatTimeTableMapper;
    private final SeatTableMapper seatTableMapper;

    @XxlJob("seatStatusUpdateJobHandler")
    @Transactional(rollbackFor = Exception.class)
    public ReturnT<String> execute(String param) {
        log.info("开始定时任务：更新过期座位状态...");

        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 查询所有比当前日期小的座位
        List<SeatTimeTable> expiredSeats = seatTimeTableMapper.selectList(new QueryWrapper<SeatTimeTable>()
                .eq("status", "0")
                .lt("date", today));

        log.info("找到 {} 个已过期的座位", expiredSeats.size());

        if (expiredSeats.isEmpty()) {
            log.info("没有需要更新的座位");
            return ReturnT.SUCCESS;
        }

        // 更新 seat_time_table 表中的座位状态
        for (SeatTimeTable seat : expiredSeats) {
            // 更新状态为已过期
            seat.setStatus("3");
        }

        // 批量更新 seat_time_table 表中的座位状态
        List<BatchResult> batchResults = seatTimeTableMapper.updateById(expiredSeats);
        log.info("过期座位状态更新成功，共更新 {} 个座位", batchResults.size());

        // 更新 seat_table 表中的座位状态
        List<String> seatIds = expiredSeats.stream().map(SeatTimeTable::getSeatId).distinct().toList();
        List<SeatTable> seatsToUpdate = seatTableMapper.selectList(new QueryWrapper<SeatTable>().in("seat_id", seatIds));

        for (SeatTable seat : seatsToUpdate) {
            // 更新状态为已过期
            seat.setStatus("3");
            // 设置更新时间
            seat.setUpdateTime(DateUtil.date());
        }

        // 批量更新 seat_table 表中的座位状态
        List<BatchResult> batchResults1 = seatTableMapper.updateById(seatsToUpdate);
        log.info("过期座位状态更新成功，共更新 {} 个座位", batchResults1.size());

        return ReturnT.SUCCESS;
    }
}
