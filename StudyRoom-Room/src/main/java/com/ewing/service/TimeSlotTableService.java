package com.ewing.service;

import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.dto.TimeSlotDto;
import com.ewing.domain.entity.TimeSlotTable;

import java.util.List;

/**
* @author ewing
* @description 针对表【time_slot_table(时间槽表)】的数据库操作Service
* @createDate 2024-10-18 22:42:28
*/
public interface TimeSlotTableService extends IService<TimeSlotTable> {
    ResultPage<List<TimeSlotDto>> getAllTimeSlots();
}
