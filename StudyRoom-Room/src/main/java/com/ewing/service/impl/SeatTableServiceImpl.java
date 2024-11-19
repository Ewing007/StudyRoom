package com.ewing.service.impl;

import Result.ResultPage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.Enum.TimeSlot;
import com.ewing.context.UserContext;
import com.ewing.context.UserContextHolder;
import com.ewing.domain.dto.SeatDto;
import com.ewing.domain.dto.req.SeatViewReqDto;
import com.ewing.domain.dto.req.UpdateSeatByAdminReqDto;
import com.ewing.domain.entity.SeatTable;
import com.ewing.domain.entity.SeatTimeTable;
import com.ewing.mapper.SeatTimeTableMapper;
import com.ewing.service.SeatTableService;
import com.ewing.mapper.SeatTableMapper;
import com.ewing.service.SeatTimeTableService;
import constant.ErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
* @author ewing
* @description 针对表【seat_table(座位表)】的数据库操作Service实现
* @createDate 2024-10-15 20:30:11
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class SeatTableServiceImpl extends ServiceImpl<SeatTableMapper, SeatTable>
    implements SeatTableService{

    private final SeatTableMapper seatTableMapper;

    private final SeatTimeTableMapper seatTimeTableMapper;

    @Override
    public ResultPage<List<SeatDto>> getSeatsByRoomIdAndTimeAndDate(SeatViewReqDto seatViewReqDto) {
//        log.info("seatViewReqDto:{}", seatViewReqDto.toString());
//        String timeRange = seatViewReqDto.getSlotId();
//        LocalDate date = seatViewReqDto.getDate().toLocalDate();
//
//        String slotNameByTimeRange = TimeSlot.getSlotNameByTimeRange(timeRange);// 将时间范围字符串转换为 slot_id
//        log.info("slotId:{}", slotNameByTimeRange);
//        List<SeatTable> seatDtos = roomTableMapper.getSeatInfoByDateAndTime(seatViewReqDto.getRoomId(), date, slotNameByTimeRange);
//        List<SeatDto> seatDtoList = BeanUtil.copyToList(seatDtos, SeatDto.class);
////        if (seatDtos == null || seatDtos.isEmpty()) {
////            return ResultPage.FAIL(ErrorEnum.NO_DATA_FOUND);
////        }
//        log.info("seatDtos:{}", seatDtos.toString());
//        return ResultPage.SUCCESS(seatDtoList);
        return null;
    }

    @Override
    public ResultPage<Void> updateSeat(UpdateSeatByAdminReqDto updateSeatByAdminReqDto) {
        String timeRange = updateSeatByAdminReqDto.getSlotId();
        LocalDate date = updateSeatByAdminReqDto.getDate().toLocalDate();

        log.info("updateSeatByAdminReqDto:{}", updateSeatByAdminReqDto.toString());
        String slotNameByTimeRange = TimeSlot.getSlotNameByTimeRange(timeRange);// 将时间范围字符串转换为 slot_id
        UpdateWrapper<SeatTimeTable> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", updateSeatByAdminReqDto.getSeatStatus())
                .eq("room_id", updateSeatByAdminReqDto.getRoomId())
                .eq("seat_id", updateSeatByAdminReqDto.getSeatId())
                .eq("slot_id", slotNameByTimeRange)
                .eq("date", date);

        int update = seatTimeTableMapper.update(null, updateWrapper);
        log.info("update seat status:{}", update);
        return update == 1 ? ResultPage.SUCCESS(ErrorEnum.SEAT_STATUS_UPDATE_SUCCESS) : ResultPage.FAIL(ErrorEnum.SEAT_STATUS_UPDATE_FAIL);
    }
//    @Override
//    public ResultPage<List<SeatDto>> getSeatsByRoomId(String roomId) {
//        //从userContext获取userContext
//        UserContext userContext = UserContextHolder.getUserContext();
//        if(ObjectUtil.isNull(userContext)) {
//            return ResultPage.FAIL(ErrorEnum.USER_NOT_PERSSIONS);
//        }
//        QueryWrapper<SeatTable> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("room_id", roomId);
//        List<SeatTable> seatList = seatTableMapper.selectList(queryWrapper);
//        if(ObjectUtil.isEmpty(seatList)) {
//            return ResultPage.FAIL(ErrorEnum.ROOM_NOT_EXIT_SEAT);
//        }
//        List<SeatDto> seatDtos = BeanUtil.copyToList(seatList, SeatDto.class);
//        return ResultPage.SUCCESS(seatDtos);
//    }
}




