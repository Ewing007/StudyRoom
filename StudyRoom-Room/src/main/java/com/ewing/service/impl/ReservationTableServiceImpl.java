package com.ewing.service.impl;

import Page.PageRespDto;
import Result.ResultPage;
import Utils.SnowUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.Enum.TimeSlot;
import com.ewing.context.UserContext;
import com.ewing.context.UserContextHolder;
import com.ewing.domain.dto.ReservationByAdminDto;
import com.ewing.domain.dto.ReservationByUserDto;
import com.ewing.domain.dto.req.BookRoomReqDto;
import com.ewing.domain.dto.req.GetAllReservationByAdminReqDto;
import com.ewing.domain.dto.req.ReservationByUserReqDto;
import com.ewing.domain.dto.req.ReservationUpdateByAdminReqDto;
import com.ewing.domain.entity.ReservationTable;
import com.ewing.domain.entity.SeatTable;
import com.ewing.domain.entity.SeatTimeTable;
import com.ewing.domain.entity.TimeSlotTable;
import com.ewing.mapper.SeatTableMapper;
import com.ewing.mapper.SeatTimeTableMapper;
import com.ewing.service.ReservationTableService;
import com.ewing.mapper.ReservationTableMapper;
import com.xxl.job.core.util.DateUtil;
import constant.ErrorEnum;

import com.ewing.mapper.TimeSlotTableMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import Exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;

/**
* @author ewing
* @description 针对表【reservation_table(自习室座位预约表)】的数据库操作Service实现
* @createDate 2024-10-15 20:30:49
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationTableServiceImpl extends ServiceImpl<ReservationTableMapper, ReservationTable>
    implements ReservationTableService{


    private final SeatTableMapper seatTableMapper;

    private final TimeSlotTableMapper timeSlotTableMapper;

    private final SeatTimeTableMapper seatTimeTableMapper;
    private final ReservationTableMapper reservationTableMapper;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultPage<Void> bookRoom(BookRoomReqDto bookRoomReqDto) {
        UserContext userContext = UserContextHolder.getUserContext();
        if (ObjectUtil.isNull(userContext)) {
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }

        String userId = userContext.getUserId();

        // 检查座位是否存在
        // 检查座位是否存在且可用
        List<String> timeSlots = bookRoomReqDto.getTime().stream()
                .map(seatId -> {
                    return TimeSlot.getSlotNameByTimeRange(seatId);
                }).collect(Collectors.toList());


        log.info("预约时间段：{}", timeSlots);
        // 检查时间段是否与其他预约冲突
        List<ReservationTable> conflictingReservations = reservationTableMapper.selectList(new QueryWrapper<ReservationTable>()
                .eq("room_id", bookRoomReqDto.getRoomId())
                .eq("seat_id", bookRoomReqDto.getSeatId())
                .eq("date", bookRoomReqDto.getDate())
                .in("slot_id", timeSlots));
        log.info("冲突的预约记录：{}", conflictingReservations);
        if (!conflictingReservations.isEmpty()) {
            return ResultPage.FAIL(ErrorEnum.RESERVATION_TIME_CONFLICT);
        }


        // 校验预约时间不能在过去
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        // 将 java.util.Date 转换为 java.time.LocalDate
        LocalDate bookingDate = bookRoomReqDto.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();


        if (bookingDate.isBefore(currentDate)) {
            return ResultPage.FAIL(ErrorEnum.RESERVATION_PAST_DATE);
        } else if (bookingDate.equals(currentDate)) {
            for (String timeSlot : timeSlots) {
                LocalTime startTime = LocalTime.parse(TimeSlot.getTimeRangeBySlotId(timeSlot).getStartTime());
                if (startTime.isBefore(currentTime)) {
                    return ResultPage.FAIL(ErrorEnum.RESERVATION_PAST_TIME);
                }
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        String dateStr = sdf.format(bookRoomReqDto.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ReservationTable> reservationTables = new ArrayList<>();
        timeSlots.forEach(timeSlot -> {
            ReservationTable reservationTable = new ReservationTable();
            reservationTable.setReservationId(SnowUtils.getSnowflakeNextIdStr());
            reservationTable.setRoomId(bookRoomReqDto.getRoomId());
            reservationTable.setSeatId(bookRoomReqDto.getSeatId());
            reservationTable.setUserId(userId);
            reservationTable.setDate(bookRoomReqDto.getDate());
            reservationTable.setSlotId(timeSlot);

            try {
                Date startTime = dateFormat.parse(dateStr + " " + TimeSlot.getTimeRangeBySlotId(timeSlot).getStartTime());
                Date endTime = dateFormat.parse( dateStr + " " + TimeSlot.getTimeRangeBySlotId(timeSlot).getEndTime());

                reservationTable.setStartTime(startTime);
                reservationTable.setEndTime(endTime);
            } catch (ParseException e) {
                log.error("解析时间失败", e);
            }
            reservationTables.add(reservationTable);
        });

        log.info("预约记录：{}", reservationTables);
        // 批量插入预约信息
        reservationTableMapper.insert(reservationTables);


        // 将 Date 转换为 Instant
        Instant instant = bookRoomReqDto.getDate().toInstant();

        // 将 Instant 转换为 ZonedDateTime，指定时区为 GMT+8
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);

        // 从 ZonedDateTime 中提取 LocalDate
        LocalDate localDate = zonedDateTime.toLocalDate();
        log.info("预约日期：{}", localDate);
        // 更新座位状态
        timeSlots.forEach(timeSlot -> {
            SeatTimeTable seatTimeTable = new SeatTimeTable();
            seatTimeTable.setStatus("1");
            log.info("更新座位时间表：{}", seatTimeTable);
            log.info("座位ID：{}", bookRoomReqDto.getSeatId());
            log.info("日期：{}", localDate);
            log.info("时间段：{}", timeSlot);
            List<SeatTimeTable> seat_id = seatTimeTableMapper.selectList(new QueryWrapper<SeatTimeTable>()
                    .eq("seat_id", bookRoomReqDto.getSeatId()));
            log.info("座位信息：{}", seat_id);
            seatTimeTable.setSeatId(bookRoomReqDto.getSeatId());
            int update1 = seatTimeTableMapper.update(seatTimeTable, new UpdateWrapper<SeatTimeTable>()
                    .eq("seat_id", bookRoomReqDto.getSeatId())
                    .eq("date", bookRoomReqDto.getDate())
                    .eq("slot_id", timeSlot));
            log.info("更新座位时间表状态：{}", update1);
            // 创建要更新的 SeatTable 对象
//            SeatTable seatTable = new SeatTable();
//            seatTable.setStatus("1");
//            int update = seatTableMapper.update(seatTable, new UpdateWrapper<SeatTable>()
//                    .eq("seat_id", bookRoomReqDto.getSeatId()));
//            log.info("更新座位状态：{}", update);
        });

        return ResultPage.SUCCESS(ErrorEnum.RESERVATION_CREATE_SUCCESS);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultPage<PageRespDto<ReservationByUserDto>> queryRoom(ReservationByUserReqDto reservationByUserReqDto) {
        // 检查用户ID是否为空
        UserContext userContext = UserContextHolder.getUserContext();
        if (ObjectUtil.isNull(userContext)) {
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        String userId = userContext.getUserId();

        // 查询用户的预约记录
        QueryWrapper<ReservationTable> queryWrapper = new QueryWrapper<ReservationTable>()
                .eq("user_id", userId)
                // 只查询未删除的记录
                .eq("del_flag", "0")
                // 指定时间范围为过去一个月
                .between("reservation_time", getStartOfMonth(), getEndOfMonth())
                // 按预约时间降序排列
                .orderByDesc("reservation_time");

        Page<ReservationTable> reservationTablePage = new Page<>(reservationByUserReqDto.getPageNum(), reservationByUserReqDto.getPageSize());
        Page<ReservationTable> page = reservationTableMapper.selectPage(reservationTablePage, queryWrapper);
        List<ReservationTable> records = page.getRecords();
        List<ReservationByUserDto> reservationByUserDtos = BeanUtil.copyToList(records, ReservationByUserDto.class);
        PageRespDto<ReservationByUserDto> pageRespDto = PageRespDto.of(reservationByUserReqDto.getPageNum(), reservationByUserReqDto.getPageSize(),page.getTotal(), reservationByUserDtos);
        // 返回结果
        return ResultPage.SUCCESS(pageRespDto);
    }

    // 获取当前月份的第一天和最后一天
    private LocalDateTime getStartOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
    }

    private LocalDateTime getEndOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultPage<Void> cancelRoom(String reservationId) {
        // 检查用户ID是否为空
        UserContext userContext = UserContextHolder.getUserContext();
        if (ObjectUtil.isNull(userContext)) {
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }
        String userId = userContext.getUserId();

        log.info("取消预约记录：{}", reservationId);
        log.info("用户ID：{}", userId);

        // 检查预约记录是否存在
        ReservationTable reservation = reservationTableMapper.selectOne(new QueryWrapper<ReservationTable>()
                .eq("reservation_id", reservationId)
                .eq("user_id", userId)
                .eq("del_flag", "0"));
        if (ObjectUtils.isNull(reservation)) {
            log.error("预约记录不存在");
            return ResultPage.FAIL(ErrorEnum.RESERVATION_NOT_FOUND);
        }

        // 检查当前时间与预约开始时间的时间差
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationTime = reservation.getReservationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        long minutesDifference = ChronoUnit.MINUTES.between(now, reservationTime);

        if (minutesDifference <= 30 && minutesDifference >= 0) {
            log.error("预约开始前30分钟内不允许取消");
            return ResultPage.FAIL(ErrorEnum.RESERVATION_CANCEL_TOO_LATE);
        }
        // 检查预约是否已经取消
        if ("1".equals(reservation.getStatus())) {
            log.info("预约记录已取消");
            return ResultPage.FAIL(ErrorEnum.RESERVATION_ALREADY_CANCELLED);
        }

        // 更新预约记录状态
        reservation.setStatus("1"); // 设置状态为已取消
        reservation.setCancelTime(new Date());
        boolean updateResult = reservationTableMapper.updateById(reservation) > 0;

        if (!updateResult) {
            log.error("更新预约记录状态失败");
            return ResultPage.FAIL(ErrorEnum.RESERVATION_CANCEL_FAILURE);
        }

        return ResultPage.SUCCESS(ErrorEnum.RESERVATION_CANCEL_SUCCESS);
    }

    @Override
    public ResultPage<PageRespDto<ReservationByAdminDto>> getAllReservations(GetAllReservationByAdminReqDto getAllReservationByAdminReqDto) {
        log.info("getAllReservations: {}", getAllReservationByAdminReqDto.toString());
        if (getAllReservationByAdminReqDto.isFetchAll()) {
            // 如果 fetchAll 为 true，获取所有失物招领
            List<ReservationTable> reservations = this.list();
            List<ReservationByAdminDto> reservationByAdminDtos = BeanUtil.copyToList(reservations, ReservationByAdminDto.class);
            log.info("lostFounds: {}", reservations);
            PageRespDto<ReservationByAdminDto> pageRespDto = PageRespDto.of(getAllReservationByAdminReqDto.getPageNum(), reservationByAdminDtos.size(), reservationByAdminDtos.size(), reservationByAdminDtos);
            return ResultPage.SUCCESS(pageRespDto);
        } else {
            // 如果 fetchAll 为 false，按分页查询

            Page<ReservationTable> page = new Page<ReservationTable>(getAllReservationByAdminReqDto.getPageNum(), getAllReservationByAdminReqDto.getPageSize());
            Page<ReservationTable> reservationTablePage = reservationTableMapper.selectPage(page, null);
            List<ReservationTable> records = reservationTablePage.getRecords();
            List<ReservationByAdminDto> reservationByAdminDtos = BeanUtil.copyToList(records, ReservationByAdminDto.class);
            PageRespDto<ReservationByAdminDto> reservationByAdminDtoPageRespDto = PageRespDto.of(getAllReservationByAdminReqDto.getPageNum(), getAllReservationByAdminReqDto.getPageSize(), reservationTablePage.getTotal(), reservationByAdminDtos);
            log.info("reservationByAdminDtoPageRespDto: {}", reservationByAdminDtoPageRespDto.getPageNum());
            log.info("reservationByAdminDtoPageRespDto: {}", reservationByAdminDtoPageRespDto.getPageSize());
            log.info("reservationByAdminDtoPageRespDto: {}", reservationByAdminDtoPageRespDto.getTotal());
            log.info("reservationByAdminDtoPageRespDto: {}", reservationByAdminDtoPageRespDto.getPageNum());
            return ResultPage.SUCCESS(reservationByAdminDtoPageRespDto);

//            Page<ReservationTable> page = new Page<>(getAllReservationByAdminReqDto.getPageNum(), getAllReservationByAdminReqDto.getPageSize());
//            Page<ReservationTable> reservationTablePage = reservationTableMapper.selectPage(page, null);
//            List<ReservationTable> reservationTables = reservationTablePage.getRecords();
//            log.info("total: {}", reservationTablePage.getTotal());
//            log.info("page: {}. size: {}", getAllReservationByAdminReqDto.getPageNum(), getAllReservationByAdminReqDto.getPageSize());
//            List<ReservationByAdminDto> reservationByAdminDtos = BeanUtil.copyToList(reservationTables, ReservationByAdminDto.class);
//            PageRespDto<ReservationByAdminDto> pageRespDto = PageRespDto.of(getAllReservationByAdminReqDto.getPageNum(), getAllReservationByAdminReqDto.getPageSize(), reservationTablePage.getTotal(), reservationByAdminDtos);
//            return ResultPage.SUCCESS(pageRespDto);
        }
    }

    @Override
    public ResultPage<Void> updateReservation(ReservationUpdateByAdminReqDto reservationUpdateByAdminReqDto) {
        ReservationTable reservationTable = BeanUtil.copyProperties(reservationUpdateByAdminReqDto, ReservationTable.class);
        UpdateWrapper<ReservationTable> reservation_id = new UpdateWrapper<ReservationTable>()
                .eq("reservation_id", reservationTable.getReservationId());
        boolean update = this.update(reservationTable, reservation_id);
        if (update) {
            return ResultPage.SUCCESS(ErrorEnum.RESERVATION_UPDATE_SUCCESS);
        } else {
            return ResultPage.FAIL(ErrorEnum.RESERVATION_UPDATE_FAILURE);
        }

    }


}









