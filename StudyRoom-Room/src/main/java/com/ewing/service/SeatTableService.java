package com.ewing.service;

import Result.ResultPage;
import com.ewing.domain.dto.SeatDto;
import com.ewing.domain.entity.SeatTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ewing
* @description 针对表【seat_table(座位表)】的数据库操作Service
* @createDate 2024-10-15 20:30:11
*/
public interface SeatTableService extends IService<SeatTable> {

//    ResultPage<List<SeatDto>> getSeatsByRoomId(String roomId);
}
