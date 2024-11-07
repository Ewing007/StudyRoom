package com.ewing.job;

import Utils.SeatGeneratorUtils;
import Utils.SnowUtils;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ewing.domain.entity.RoomTable;
import com.ewing.domain.entity.SeatTable;
import com.ewing.domain.entity.SeatTimeTable;
import com.ewing.mapper.RoomTableMapper;
import com.ewing.mapper.SeatTableMapper;
import com.ewing.mapper.SeatTimeTableMapper;
import com.ewing.service.impl.RoomTableServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ewing
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SeatGenerateJobHandler {

    private final RoomTableServiceImpl roomTableService;

    private final SeatTableMapper seatTableMapper;

    private final SeatTimeTableMapper seatTimeTableMapper;

    private final RoomTableMapper roomTableMapper;

    @XxlJob("seatGenerateExpireJobHandler")
    @Transactional(rollbackFor = Exception.class)
    public void execute(String param) {
        log.info("开始定时任务：生成未来三天的座位信息...");
        // 获取所有活动的自习室
        List<RoomTable> activeRooms = roomTableService.getAllActiveStudyRooms().getData();

        log.info("共有 {} 个自习室需要生成座位信息", activeRooms.size());
        // 生成未来三天的日期
        List<LocalDate> dates = generateDatesForNextThreeDays();

        log.info("生成日期：{}", dates);
        // 遍历每个自习室，生成座位信息
        for (RoomTable room : activeRooms) {
            List<String> slotIds = roomTableMapper.getSlotIdsByRoomId(room.getRoomId());
            for (LocalDate date : dates) {
                List<SeatTimeTable> seatTimeTables = seatTimeTableMapper.selectList(new QueryWrapper<SeatTimeTable>()
                        .eq("room_id", room.getRoomId())
                        .eq("date", date));
                if(ObjectUtil.isNotEmpty(seatTimeTables)) {
                    log.info("日期 {} 的座位已存在，无需生成新座位", date);
                    continue;
                };
//                int remainingCapacity = room.getCapacity() - existingSeats.size();
//                log.info("自习室 {} 在日期 {} 剩余容量为 {}", room.getName(), date, remainingCapacity);
//                log.info("自习室信息：{}", room);
//                log.info("已有 {} 座位信息", existingSeats);
//                if (remainingCapacity > 0) {
                List<SeatTable> newSeats = generateSeatsAndTimeSlots(room, room.getCapacity(), List.of(date), slotIds);
                log.info("座位信息：{}", newSeats);
                log.info("生成 {} 张新座位", newSeats.size());
                    // 批量插入新的座位信息
//                seatTableMapper.insert(newSeats);
//                } else {
//                    log.info("日期 {} 的座位已满，无需生成新座位", date);
//                }
            }
        }

        log.info("定时任务结束！");
    }

    private List<LocalDate> generateDatesForNextThreeDays() {
        LocalDate now = LocalDate.now();
        return IntStream.range(1, 4)
                .mapToObj(now::plusDays)
                .collect(Collectors.toList());
    }

    private List<SeatTable> generateSeatsAndTimeSlots(RoomTable room, int capacity, List<LocalDate> dates, List<String> timeSlots) {
        List<String> seatNumbers = SeatGeneratorUtils.generateSeatNumbers(capacity);
        log.info("seatNumbers: {}", seatNumbers);

        List<SeatTable> seats = seatNumbers.stream()
                .map(seatNumber -> createSeat(room, seatNumber))
                .collect(Collectors.toList());

        List<SeatTimeTable> seatTimes = seats.stream()
                .flatMap(seat -> dates.stream()
                        .flatMap(date -> timeSlots.stream()
                                .map(slotId -> createSeatTime(seat, slotId, date))))
                .collect(Collectors.toList());

        // 批量插入座位和时间段记录
        seatTableMapper.insert(seats);
        seatTimeTableMapper.insert(seatTimes);

        return seats;
    }

    private SeatTable createSeat(RoomTable room, String seatNumber) {
        SeatTable seat = new SeatTable();
        seat.setSeatId(SnowUtils.getSnowflakeNextIdStr());
        seat.setRoomId(room.getRoomId());
        seat.setSeatNumber(seatNumber);
        // 默认状态为可用
        seat.setStatus("0");
        seat.setCreateTime(new DateTime());
        seat.setUpdateTime(new DateTime());
        seat.setDelFlag("0");
        return seat;
    }

    private SeatTimeTable createSeatTime(SeatTable seat, String slotId, LocalDate date) {
        SeatTimeTable seatTime = new SeatTimeTable();
        seatTime.setSeatId(seat.getSeatId());
        seatTime.setSlotId(slotId);
        // 默认状态为可用
        seatTime.setStatus("0");
        seatTime.setRoomId(seat.getRoomId());
        seatTime.setSeatNumber(seat.getSeatNumber());
        seatTime.setDate(date);
        return seatTime;
    }
}
