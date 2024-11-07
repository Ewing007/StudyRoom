package com.ewing.service.impl;

import Result.ResultPage;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.domain.dto.TimeSlotDto;
import com.ewing.domain.entity.TimeSlotTable;
import com.ewing.mapper.TimeSlotTableMapper;
import com.ewing.service.TimeSlotTableService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ewing
* @description 针对表【time_slot_table(时间槽表)】的数据库操作Service实现
* @createDate 2024-10-18 22:42:28
*/
@Service
@RequiredArgsConstructor
public class TimeSlotTableServiceImpl extends ServiceImpl<TimeSlotTableMapper, TimeSlotTable>
    implements TimeSlotTableService {

    private final TimeSlotTableMapper timeSlotTableMapper;

    @Override
    public ResultPage<List<TimeSlotDto>> getAllTimeSlots() {
        List<TimeSlotTable> list = this.list();
        List<TimeSlotDto> timeSlotDtos = BeanUtil.copyToList(list, TimeSlotDto.class);
        return ResultPage.SUCCESS(timeSlotDtos);
    }
}




