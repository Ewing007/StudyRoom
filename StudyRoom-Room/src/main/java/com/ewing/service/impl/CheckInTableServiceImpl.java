package com.ewing.service.impl;

import Result.ResultPage;
import Utils.AESUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ewing.domain.entity.CheckInTable;
import com.ewing.domain.entity.ReservationTable;
import com.ewing.mapper.CheckInTableMapper;
import com.ewing.mapper.ReservationTableMapper;
import com.ewing.service.CheckInTableService;
import constant.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
* @author ewing
* @description 针对表【check_in_table(自习室签到表)】的数据库操作Service实现
* @createDate 2024-11-28 17:57:42
*/
@Service
@RequiredArgsConstructor
public class CheckInTableServiceImpl extends ServiceImpl<CheckInTableMapper, CheckInTable>
    implements CheckInTableService {

    private final CheckInTableMapper checkInTableMapper;
    @Override
    public ResultPage<Void> checkIn(String encryptedUserId, String encryptedReservationId, String expirationTime) throws Exception {
        // 解密预约ID和开始时间
        String user_id = AESUtil.decrypt(encryptedUserId);
        String reservation_id = AESUtil.decrypt(encryptedReservationId);

        // 解析时间
        LocalDateTime expiration_time = LocalDateTime.parse(expirationTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // 检查当前时间是否在有效期内
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(expiration_time)) {
            return ResultPage.FAIL(ErrorEnum.CHECK_IN_EXPIRED);
        }

        LambdaQueryWrapper<CheckInTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CheckInTable::getReservationId, reservation_id)
               .eq(CheckInTable::getUserId, user_id);
        CheckInTable checkInTable = checkInTableMapper.selectOne(queryWrapper);
        if("1".equals(checkInTable.getStatus())) {
            return ResultPage.FAIL(ErrorEnum.CHECK_IN_ALREADY_CHECKED);
        }

        UpdateWrapper<CheckInTable> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reservation_id", reservation_id)
               .eq("user_id", user_id);

        CheckInTable update = new CheckInTable();
        update.setStatus("1");
        update.setCheckInTime(new DateTime());
        checkInTableMapper.update(update, updateWrapper);
        return ResultPage.SUCCESS(ErrorEnum.CHECK_IN_SUCCESS);
    }
}




