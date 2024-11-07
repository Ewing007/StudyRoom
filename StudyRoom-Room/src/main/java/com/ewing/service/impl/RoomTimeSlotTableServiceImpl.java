package com.ewing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.domain.entity.RoomTimeSlotTable;
import com.ewing.service.RoomTimeSlotTableService;
import com.ewing.mapper.RoomTimeSlotTableMapper;
import org.springframework.stereotype.Service;

/**
* @author ewing
* @description 针对表【room_time_slot_table(自习室开放时间段表)】的数据库操作Service实现
* @createDate 2024-11-02 21:42:21
*/
@Service
public class RoomTimeSlotTableServiceImpl extends ServiceImpl<RoomTimeSlotTableMapper, RoomTimeSlotTable>
    implements RoomTimeSlotTableService{

}




