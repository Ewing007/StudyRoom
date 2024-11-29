package com.ewing.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ewing.domain.entity.CheckInTable;
import com.ewing.mapper.CheckInTableMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import constant.SystemConfigConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ewing
 * @Date: 2024-11-28-17:44
 * @Description: 定时任务处理器，更新未签到且已过期的签到信息
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateExpiredCheckInJobHandler {
    private final CheckInTableMapper checkInTableMapper;

    private final KafkaTemplate<String, HashMap<String, String>> kafkaTemplate;

    @XxlJob("updateExpiredCheckInJob")
    public ReturnT<String> execute() {
        log.info("开始更新未签到且已过期的签到信息任务");

        try {
            LocalDateTime now = LocalDateTime.now();

            // 查询未签到且已过期的记录
            List<CheckInTable> expiredCheckIns = checkInTableMapper.selectList(
                    new LambdaQueryWrapper<CheckInTable>()
                            .lt(CheckInTable::getExpireTime, now)
                            .eq(CheckInTable::getStatus, "0")
                            .isNull(CheckInTable::getCheckInTime)
            );

            for (CheckInTable checkIn : expiredCheckIns) {
                // 更新签到状态为已过期
                checkIn.setStatus("2");
                checkInTableMapper.updateById(checkIn);
                // 发送未签到用户ID到Kafka主题
                String userId = checkIn.getUserId();
                HashMap<String, String> map = new HashMap<>();
                map.put(userId, SystemConfigConstant.USER_CREDIT_DEDUCTION_NOTIFICATION);
                kafkaTemplate.send(SystemConfigConstant.USER_CREDIT_DEDUCTION_TOPIC, map);
            }
        } catch (Exception e) {
            log.error("更新未签到且已过期的签到信息任务失败: " + e.getMessage(), e);
            return ReturnT.FAIL;
        }

        log.info("更新未签到且已过期的签到信息任务完成");
        return ReturnT.SUCCESS;
    }
}
