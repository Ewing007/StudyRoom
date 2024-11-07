package com.ewing.service.impl;

import Page.PageRespDto;
import Result.ResultPage;
import Utils.SeatGeneratorUtils;
import Utils.SnowUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.Enum.TimeSlot;
import com.ewing.context.UserContext;
import com.ewing.context.UserContextHolder;
import com.ewing.domain.dto.RoomByAminDto;
import com.ewing.domain.dto.RoomDto;
import com.ewing.domain.dto.SeatDto;
import com.ewing.domain.dto.req.*;
import com.ewing.domain.entity.*;
import com.ewing.mapper.*;
import com.ewing.service.ReservationTableService;
import com.ewing.service.RoomTableService;
import Exception.BusinessException;
import com.ewing.service.SeatTableService;
import constant.DataBaseConstant;
import constant.ErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
* @author ewing
* @description 针对表【room_table(自习室表)】的数据库操作Service实现
* @createDate 2024-10-13 14:05:02
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomTableServiceImpl extends ServiceImpl<RoomTableMapper, RoomTable>
    implements RoomTableService {

    private final RoomTableMapper roomTableMapper;

    public final SeatTableMapper seatTableMapper;

    private final SeatTableService seatTableService;

    private final ReservationTableService reservationTableService;
    private final ReservationTableMapper reservationTableMapper;

    private final RoomTimeSlotTableMapper roomTimeSlotMapper;

    private final SeatTimeTableMapper seatTimeTableMapper;
//    @Override
//    public ResultPage<Void> createStudyRoom(RoomCreateReqDto roomCreateReqDto) {
//        //从userContext获取userContext
//        UserContext userContext = UserContextHolder.getUserContext();
//        if(ObjectUtil.isNull(userContext)) {
//            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
//        }
//
//        String adminId = userContext.getUserId();
//
//        // 创建一个 RoomTable 对象
//        RoomTable room = new RoomTable();
//        // 设置自习室属性
//        room.setRoomId(SnowUtils.getSnowflakeNextIdStr());
//        room.setName(roomCreateReqDto.getName());
//        room.setLocation(roomCreateReqDto.getLocation());
//        room.setCapacity(roomCreateReqDto.getCapacity());
//        room.setAmenities(roomCreateReqDto.getAmenities());
////        room.setOpeningHours(roomCreateReqDto.getOpeningHours());
//        room.setDescription(roomCreateReqDto.getDescription());
//        room.setFloor(roomCreateReqDto.getFloor());
//        room.setType(roomCreateReqDto.getType());
//        room.setImageUrl(roomCreateReqDto.getImageUrl());
//        // 关联管理员 ID
//        room.setUserId(adminId);
//        room.setCreateTime(new DateTime());
//        room.setUpdateTime(new DateTime());
//
//        // 保存自习室信息
//        boolean save = this.save(room);
//        if (!save) {
//            return ResultPage.FAIL(ErrorEnum.ROOM_CREATE_FAILURE);
//        }
//
////        // 根据自习室容量生成座位
////        int capacity = roomCreateReqDto.getCapacity();
////        List<String> seatNumbers = SeatGeneratorUtils.generateSeatNumbers(capacity);
////        List<SeatTable> seats = seatNumbers.stream()
////                .map(seatNumber -> {
////                    SeatTable seat = new SeatTable();
////                    seat.setSeatId(SnowUtils.getSnowflakeNextIdStr());
////                    seat.setRoomId(room.getRoomId());
////                    seat.setSeatNumber(seatNumber);
////                    seat.setStatus("0"); // 默认状态为可用
////                    seat.setCreateTime(new DateTime());
////                    seat.setUpdateTime(new DateTime());
////                    seat.setDelFlag("0");
////                    return seat;
////                })
////                .toList();
//
//        // 保存自习室与时间段的关系
//        saveRoomTimeSlots(room.getRoomId(), roomCreateReqDto.getOpeningHours());
//
//        // 生成从当前日期开始的三天日期
//        List<LocalDateTime> dates = generateDatesForNextThreeDays();
//
//        // 根据自习室容量生成座位
//        generateSeats(room, roomCreateReqDto.getCapacity(), dates);
//
//        // 批量插入座位
////        seatTableMapper.insert(seats);
//
//        return ResultPage.SUCCESS(ErrorEnum.ROOM_CREATE_SUCCESS);
//    }
//
//    private void saveRoomTimeSlots(String roomId, List<String> selectedTimeSlots) {
//        for (String slotId : selectedTimeSlots) {
//            RoomTimeSlotTable roomTimeSlot = new RoomTimeSlotTable();
//            roomTimeSlot.setRoomId(roomId);
//            roomTimeSlot.setSlotId(slotId);
//            roomTimeSlotMapper.insert(roomTimeSlot);
//        }
//    }
//    private SeatTable createSeat(RoomTable room, String seatNumber, LocalDateTime date) {
//        SeatTable seat = new SeatTable();
//        seat.setSeatId(SnowUtils.getSnowflakeNextIdStr());
//        seat.setRoomId(room.getRoomId());
//        seat.setSeatNumber(seatNumber);
//        seat.setStatus("0"); // 默认状态为可用
//        seat.setDate(new DateTime());
//        seat.setCreateTime(new DateTime());
//        seat.setUpdateTime(new DateTime());
//        seat.setDelFlag("0");
//        return seat;
//    }
//
//    public boolean generateSeats(RoomTable room, int capacity, List<LocalDateTime> dates) {
//        List<String> seatNumbers = SeatGeneratorUtils.generateSeatNumbers(capacity);
//        log.info("seatNumbers:{}", seatNumbers);
//        List<SeatTable> seats = new ArrayList<>();
//
//        for (LocalDateTime date : dates) {
//            List<SeatTable> dailySeats = seatNumbers.stream()
//                    .map(seatNumber -> createSeat(room, seatNumber, date))
//                    .collect(Collectors.toList());
//            seats.addAll(dailySeats);
//        }
//
//        return seats;
//    }

    @Override
    @Transactional
    public ResultPage<Void> createStudyRoom(RoomCreateReqDto roomCreateReqDto) {
        // 从 userContext 获取 userContext
        UserContext userContext = UserContextHolder.getUserContext();
        if (ObjectUtil.isNull(userContext)) {
            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
        }

        String adminId = userContext.getUserId();

        // 创建并保存自习室信息
        RoomTable room = createAndSaveRoom(roomCreateReqDto, adminId);
        if (room == null) {
            return ResultPage.FAIL(ErrorEnum.ROOM_CREATE_FAILURE);
        }

        // 保存自习室与时间段的关系
        saveRoomTimeSlots(room.getRoomId(), roomCreateReqDto.getOpeningHours());

        // 生成从当前日期开始的三天日期
        List<LocalDate> dates = generateDatesForNextThreeDays();

        // 根据自习室容量生成座位及时间段记录
        generateSeatsAndTimeSlots(room, roomCreateReqDto.getCapacity(), dates, roomCreateReqDto.getOpeningHours());

        return ResultPage.SUCCESS(ErrorEnum.ROOM_CREATE_SUCCESS);
    }

    private RoomTable createAndSaveRoom(RoomCreateReqDto roomCreateReqDto, String adminId) {
        RoomTable room = new RoomTable();
        room.setRoomId(SnowUtils.getSnowflakeNextIdStr());
        room.setName(roomCreateReqDto.getName());
        room.setLocation(roomCreateReqDto.getLocation());
        room.setCapacity(roomCreateReqDto.getCapacity());
        room.setAmenities(roomCreateReqDto.getAmenities());
        room.setDescription(roomCreateReqDto.getDescription());
        room.setFloor(roomCreateReqDto.getFloor());
        room.setType(roomCreateReqDto.getType());
        room.setImageUrl(roomCreateReqDto.getImageUrl());
        room.setUserId(adminId);
        room.setCreateTime(new DateTime());
        room.setUpdateTime(new DateTime());

        boolean save = this.save(room);
        return save ? room : null;
    }

    private void saveRoomTimeSlots(String roomId, List<String> selectedTimeSlots) {
        List<RoomTimeSlotTable> roomTimeSlots = selectedTimeSlots.stream()
                .map(slotId -> {
                    RoomTimeSlotTable roomTimeSlot = new RoomTimeSlotTable();
                    roomTimeSlot.setRoomId(roomId);
                    roomTimeSlot.setSlotId(slotId);
                    return roomTimeSlot;
                })
                .collect(Collectors.toList());

        roomTimeSlotMapper.insert(roomTimeSlots);
    }

    private void generateSeatsAndTimeSlots(RoomTable room, int capacity, List<LocalDate> dates, List<String> timeSlots) {
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
    }

    private SeatTable createSeat(RoomTable room, String seatNumber) {
        SeatTable seat = new SeatTable();
        seat.setSeatId(SnowUtils.getSnowflakeNextIdStr());
        seat.setRoomId(room.getRoomId());
        seat.setSeatNumber(seatNumber);
        seat.setStatus("0"); // 默认状态为可用
        seat.setCreateTime(new DateTime());
        seat.setUpdateTime(new DateTime());
        seat.setDelFlag("0");
        return seat;
    }

    private SeatTimeTable createSeatTime(SeatTable seat, String slotId, LocalDate date) {
        SeatTimeTable seatTime = new SeatTimeTable();
        seatTime.setSeatId(seat.getSeatId());
        seatTime.setSlotId(slotId);
        seatTime.setStatus("0"); // 默认状态为可用
        seatTime.setRoomId(seat.getRoomId());
        seatTime.setSeatNumber(seat.getSeatNumber());
        seatTime.setDate(date);
        return seatTime;
    }

    private List<LocalDate> generateDatesForNextThreeDays() {
        LocalDate now = LocalDate.now();
        return IntStream.range(0, 3)
                .mapToObj(now::plusDays)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public ResultPage<Void> updateStudyRoom(RoomUpdateReqDto roomUpdateReqDto) {
        RoomTable roomTable = BeanUtil.copyProperties(roomUpdateReqDto, RoomTable.class);
        roomTable.setUpdateTime(new Date());

        // 获取原始的房间信息
        QueryWrapper<RoomTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataBaseConstant.RoomTable.ROOM_ID, roomTable.getRoomId());
        RoomTable originalRoom = roomTableMapper.selectOne(queryWrapper);

        // 处理 capacity 变化
        if (originalRoom != null && !originalRoom.getCapacity().equals(roomTable.getCapacity())) {
            // 删除所有座位数据
            seatTableMapper.deleteByRoomId(roomTable.getRoomId());
            // 删除所有座位时间段数据
            seatTimeTableMapper.deleteByRoomId(roomTable.getRoomId());
            log.info("getTimeSlotsByRoomId(roomTable.getRoomId()).getData():{}", getTimeSlotsByRoomId(roomTable.getRoomId()).getData());
            generateSeatsAndTimeSlots(roomTable, roomTable.getCapacity(), generateDatesForNextThreeDays(), roomTableMapper.getSlotIdsByRoomId(roomTable.getRoomId()));
        }

        // 处理 currentStatus 变化
        if (originalRoom != null && !"2".equals(originalRoom.getCurrentStatus()) && "2".equals(roomTable.getCurrentStatus())) {
            // 将所有关联座位状态设置为 2
            seatTableMapper.updateStatusByRoomId(roomTable.getRoomId(), "2");
            // 将所有关联座位时间段状态设置为 2
            seatTimeTableMapper.updateStatusByRoomId(roomTable.getRoomId(), "2");
        }

        //
        UpdateWrapper<RoomTable> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(DataBaseConstant.RoomTable.ROOM_ID, roomTable.getRoomId());
        boolean updateResult = roomTableMapper.update(roomTable, updateWrapper) > 0;

        if (updateResult) {
            return ResultPage.SUCCESS(ErrorEnum.ROOM_UPDATE_SUCCESS);
        } else {
            return ResultPage.FAIL(ErrorEnum.ROOM_UPDATE_FAILURE);
        }
    }

    @Override
    @Transactional
    public ResultPage<Void> deleteStudyRoom(String roomId) throws BusinessException {
//        boolean result = this.removeById(roomId);
        RoomTable room = getOne(new QueryWrapper<RoomTable>().eq(DataBaseConstant.RoomTable.ROOM_ID, roomId));
        if (ObjectUtil.isNull(room)) {
            return ResultPage.FAIL(ErrorEnum.ROOM_NOT_EXIT);
        }

        // 检查预约表是否有相关记录
        // 检查座位表是否有相关记录
        List<SeatTable> seats = seatTableMapper.selectList(new QueryWrapper<SeatTable>().eq(DataBaseConstant.RoomTable.ROOM_ID, roomId));
        if (ObjectUtil.isEmpty(seats)) {
            throw new BusinessException(ErrorEnum.ROOM_NOT_EXIT_SEAT);
        }

        // 检查预约表是否有相关记录
        List<String> seatIds = seats.stream().map(SeatTable::getSeatId).toList();
        List<ReservationTable> reservations = reservationTableMapper.selectList(new QueryWrapper<ReservationTable>().in(DataBaseConstant.RESVERATIONTABLE.SEAT_ID, seatIds));
        if (ObjectUtil.isEmpty(reservations)) {
            throw new BusinessException(ErrorEnum.ROOM_NOT_EXIT_RELATION);
        }
        roomTableMapper.deleteRoomAndRelatedRecords(roomId);

        // 更新自习室表的 del_flag
        room.setDelFlag("1");
        room.setUpdateTime(new DateTime());
        updateById(room);

        // 批量更新座位表的 del_flag
        seats.forEach(seat -> {
            seat.setDelFlag("1");
            seat.setUpdateTime(new DateTime());
        });
        seatTableMapper.updateById(seats);
//        seatTableService.updateBatchById(seats);
        // 批量更新预约表的 del_flag
        reservations.forEach(reservation -> {
            reservation.setDelFlag("1");
        });
        reservationTableMapper.updateById(reservations);
//        reservationTableService.updateBatchById(reservations);

        return ResultPage.SUCCESS(ErrorEnum.ROOM_DELETE_SUCCESS);
    }

    @Override
    public ResultPage<List<RoomDto>> getAllStudyRooms() {
//        List<RoomTable> list = this.list();
        List<RoomTable> roomTables = roomTableMapper.selectAllActiveRooms();
        log.info("roomTables:{}", roomTables);
        List<RoomDto> roomDtos = BeanUtil.copyToList(roomTables, RoomDto.class);
        return ResultPage.SUCCESS(roomDtos);
    }


//    public List<LocalDateTime> generateDatesForNextThreeDays() {
//        LocalDateTime now = LocalDateTime.now();
//        return IntStream.range(0, 3)
//                .mapToObj(i -> now.plusDays(i))
//                .collect(Collectors.toList());
//    }


    public ResultPage<List<RoomTable>> getAllActiveStudyRooms() {
//        List<RoomTable> list = this.list();
        List<RoomTable> roomTables = roomTableMapper.selectAllActiveRooms();
        return ResultPage.SUCCESS(roomTables);
    };


//    private synchronized String generateUniqueSeatId() {
//        try {
//            Thread.sleep(1); // 微小的延迟，确保时间戳不同
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        return SnowUtils.getSnowflakeNextIdStr();
//    }
@Override
public ResultPage<PageRespDto<RoomDto>> queryByCondition(RoomQueryByConditionReqDto roomQueryByConditionReqDto) {

    // 构建查询条件
    Map<String, Object> params = new HashMap<>();
    addParamsIfNotNull(params, "name", roomQueryByConditionReqDto.getName());
    addParamsIfNotNull(params, "capacity", roomQueryByConditionReqDto.getCapacity());
    addParamsIfNotEmpty(params, "amenities", roomQueryByConditionReqDto.getAmenities());
    addParamsIfNotEmpty(params, "floor", roomQueryByConditionReqDto.getFloor());
    addParamsIfNotEmpty(params, "type", roomQueryByConditionReqDto.getType());
    addParamsIfNotEmpty(params, "currentStatus", roomQueryByConditionReqDto.getCurrentStatus());
    addParamsIfNotEmpty(params, "location", roomQueryByConditionReqDto.getLocation());
    addParamsIfNotEmpty(params, "description", roomQueryByConditionReqDto.getDescription());

    if (roomQueryByConditionReqDto.isFetchAll()) {
        // 如果 fetchAll 为 true，获取所有符合条件的自习室
        List<RoomTable> roomList = roomTableMapper.queryByCondition(params);
        List<RoomDto> roomDtos = BeanUtil.copyToList(roomList, RoomDto.class);
        int total = roomDtos.size();
        PageRespDto<RoomDto> pageRespDto = PageRespDto.of(1, total, total, roomDtos);
        log.info("pageRespDto:{}", pageRespDto.toString());
        return ResultPage.SUCCESS(pageRespDto);
    } else {
        // 如果 fetchAll 为 false，按分页查询
        IPage<RoomTable> page = new Page<>(roomQueryByConditionReqDto.getPageNum(), roomQueryByConditionReqDto.getPageSize());
        IPage<RoomTable> roomPage = roomTableMapper.queryByConditionPage(page, params);
        List<RoomTable> roomList = roomPage.getRecords();
        List<RoomDto> roomDtos = BeanUtil.copyToList(roomList, RoomDto.class);
        PageRespDto<RoomDto> pageRespDto = PageRespDto.of(roomQueryByConditionReqDto.getPageNum(), roomQueryByConditionReqDto.getPageSize(), roomPage.getTotal(), roomDtos);
        return ResultPage.SUCCESS(pageRespDto);
    }
}



    private void addParamsIfNotNull(Map<String, Object> params, String key, Object value) {
        if (value != null) {
            params.put(key, value);
        }
    }

    private void addParamsIfNotEmpty(Map<String, Object> params, String key, String value) {
        if (value != null && !value.isEmpty()) {
            params.put(key, value);
        }
    }

    @Override
    public ResultPage<List<String>> getTimeSlotsByRoomId(String roomId) {
        List<String> slotIds = roomTableMapper.getSlotIdsByRoomId(roomId);


        List<String> timeSlots = slotIds.stream()
                .map(TimeSlot::fromSlotId)
                .map(timeSlot -> timeSlot.getStartTime() + " - " + timeSlot.getEndTime())
                .collect(Collectors.toList());

        return ResultPage.SUCCESS(timeSlots);
    }

    @Override
    public ResultPage<Set<LocalDate>> getDateByRoomId(String roomId) {
        Set<LocalDate> dates = roomTableMapper.getDatesByRoomId(roomId).stream()
                .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .collect(Collectors.toSet());
        return ResultPage.SUCCESS(dates);
    }

    @Override
    public ResultPage<List<SeatDto>> getSeatInfoByDateAndTime(SeatViewReqDto seatViewReqDto) {
        log.info("seatViewReqDto:{}", seatViewReqDto.toString());
        String timeRange = seatViewReqDto.getSlotId();
        LocalDate date = seatViewReqDto.getDate().toLocalDate();

        String slotNameByTimeRange = TimeSlot.getSlotNameByTimeRange(timeRange);// 将时间范围字符串转换为 slot_id
        log.info("slotId:{}", slotNameByTimeRange);
        List<SeatTable> seatDtos = roomTableMapper.getSeatInfoByDateAndTime(seatViewReqDto.getRoomId(), date, slotNameByTimeRange);
        List<SeatDto> seatDtoList = BeanUtil.copyToList(seatDtos, SeatDto.class);
//        if (seatDtos == null || seatDtos.isEmpty()) {
//            return ResultPage.FAIL(ErrorEnum.NO_DATA_FOUND);
//        }
        log.info("seatDtos:{}", seatDtos.toString());
        return ResultPage.SUCCESS(seatDtoList);
    }

    @Override
    public ResultPage<PageRespDto<RoomByAminDto>> getAllRoomsByAdmin(GetAllRoomByAdminReqDto getAllRoomByAdminReqDto) {
        if (getAllRoomByAdminReqDto.isFetchAll()) {
            List<RoomTable> roomTables = this.list();
            List<RoomByAminDto> roomByAminDtos = BeanUtil.copyToList(roomTables, RoomByAminDto.class);
            int total = roomByAminDtos.size();
            PageRespDto<RoomByAminDto> pageRespDto = PageRespDto.of(getAllRoomByAdminReqDto.getPageNum(), getAllRoomByAdminReqDto.getPageSize(), total, roomByAminDtos);
            return ResultPage.SUCCESS(pageRespDto);
        } else {

            IPage<RoomTable> page = new Page<>(getAllRoomByAdminReqDto.getPageNum(), getAllRoomByAdminReqDto.getPageSize());
            IPage<RoomTable> roomPage = roomTableMapper.selectPage(page, null);
            List<RoomTable> roomTables = roomPage.getRecords();
            List<RoomByAminDto> roomByAminDtos = BeanUtil.copyToList(roomTables, RoomByAminDto.class);
            PageRespDto<RoomByAminDto> pageRespDto = PageRespDto.of(getAllRoomByAdminReqDto.getPageNum(), getAllRoomByAdminReqDto.getPageSize(), roomPage.getTotal(), roomByAminDtos);
            return ResultPage.SUCCESS(pageRespDto);
        }
    }

}




