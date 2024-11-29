package com.ewing.job;

import Utils.AESUtil;
import Utils.SnowUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ewing.domain.dto.CheckInDto;
import com.ewing.domain.entity.CheckInTable;
import com.ewing.domain.entity.ReservationTable;
import com.ewing.manager.QRCodeGeneratorManager;
import com.ewing.mapper.CheckInTableMapper;
import com.ewing.mapper.ReservationTableMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import constant.SystemConfigConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import cn.hutool.core.bean.BeanUtil;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ewing
 * @Date: 2024-11-28-17:44
 * @Description:
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class QRCodeJobHandler {
    private final ReservationTableMapper reservationTableMapper;

    private final KafkaTemplate<String, CheckInDto> kafkaTemplate;
    private final CheckInTableMapper checkInTableMapper;
    @XxlJob("generateQRCodeJob")
    public ReturnT<String> execute() {
        log.info("开始生成二维码任务");

        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = now.plusMinutes(10);
            LocalDateTime endTime = startTime.plusMinutes(1);

            log.info("当前时间: " + now);
            log.info("开始时间: " + startTime);
            log.info("结束时间: " + endTime);
            // 查询符合条件的预约记录
            List<ReservationTable> reservations = reservationTableMapper.selectList(
                    new LambdaQueryWrapper<ReservationTable>()
                            .eq(ReservationTable::getStatus, "0")
                            .ge(ReservationTable::getStartTime, startTime)
                            .le(ReservationTable::getStartTime, endTime)
            );
            for (ReservationTable reservation : reservations) {
                log.info("预约记录: " + reservation);
                String reservationId = reservation.getReservationId();
                String userId = reservation.getUserId();

                // 设置二维码过期时间为生成时间后的15分钟
                LocalDateTime expirationTime = now.plusMinutes(15);

                //加密用户id和预约id和开始时间和过期时间
                String encryptedUserId = AESUtil.encrypt(userId);
                String encryptedReservationId = AESUtil.encrypt(reservationId);
                String encryptedExpirationTime = AESUtil.encrypt(expirationTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                // 生成二维码内容
                String qrContent ="http://localhost:8888/StudyRoom-User/qr-code/check-in?" +
                        "user_id=" + encryptedUserId +
                        "&reservation_id=" + encryptedReservationId +
                        "&expiration_time=" + encryptedExpirationTime;

                // 生成二维码
                String generateQRCodeBase64 = QRCodeGeneratorManager.generateQRCodeBase64(qrContent, 200, 200);

                // 将二维码路径保存到数据库
                CheckInTable checkInTable = new CheckInTable();
                checkInTable.setQrCodeId(SnowUtils.getSnowflakeNextIdStr());
                checkInTable.setReservationId(reservationId);
                checkInTable.setUserId(userId);
                checkInTable.setCheckInQrCode(generateQRCodeBase64);
                Date expireDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
                checkInTable.setExpireTime(expireDate);
                checkInTableMapper.insert(checkInTable);

                // 发送消息到kafka
                CheckInDto checkInDto = BeanUtil.copyProperties(checkInTable, CheckInDto.class);
                kafkaTemplate.send(SystemConfigConstant.CHECK_IN_TOPIC, checkInDto);

            }
        } catch (Exception e) {
            log.info("生成二维码任务失败: " + e.getMessage());
            e.printStackTrace();
        }

        log.info("生成二维码任务完成");
        return ReturnT.SUCCESS;
    }
}