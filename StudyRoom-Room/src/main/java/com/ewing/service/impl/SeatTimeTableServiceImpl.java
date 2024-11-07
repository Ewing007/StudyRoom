package com.ewing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ewing.domain.entity.SeatTimeTable;
import com.ewing.service.SeatTimeTableService;
import com.ewing.mapper.SeatTimeTableMapper;
import org.springframework.stereotype.Service;

/**
* @author ewing
* @description 针对表【seat_time_table(座位时间段表)】的数据库操作Service实现
* @createDate 2024-11-02 21:42:34
*/
@Service
public class SeatTimeTableServiceImpl extends ServiceImpl<SeatTimeTableMapper, SeatTimeTable>
    implements SeatTimeTableService{

}




