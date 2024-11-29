package com.ewing.service;


import Result.ResultPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ewing.domain.entity.CheckInTable;

/**
* @author ewing
* @description 针对表【check_in_table(自习室签到表)】的数据库操作Service
* @createDate 2024-11-28 17:57:42
*/
public interface CheckInTableService extends IService<CheckInTable> {

    ResultPage<Void> checkIn(String encryptedUserId, String encryptedReservationId, String expirationTime) throws Exception;
}
