package com.ewing.service.impl;

import Result.ResultPage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.context.UserContext;
import com.ewing.context.UserContextHolder;
import com.ewing.domain.dto.SeatDto;
import com.ewing.domain.entity.SeatTable;
import com.ewing.service.SeatTableService;
import com.ewing.mapper.SeatTableMapper;
import constant.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ewing
* @description 针对表【seat_table(座位表)】的数据库操作Service实现
* @createDate 2024-10-15 20:30:11
*/
@Service
@RequiredArgsConstructor
public class SeatTableServiceImpl extends ServiceImpl<SeatTableMapper, SeatTable>
    implements SeatTableService{

    private final SeatTableMapper seatTableMapper;
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




