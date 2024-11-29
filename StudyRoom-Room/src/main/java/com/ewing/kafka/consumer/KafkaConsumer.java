package com.ewing.kafka.consumer;
import constant.SystemConfigConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: Ewing
 * @Date: 2024-11-22-13:04
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

//    @KafkaListener(topics = SystemConfigConstant.SYSTEM_MESSAGE_TOPIC, groupId = SystemConfigConstant.MESSAGE_GROUP_ID)
//    public void consumeSystemInNotificationsMessage(ConsumerRecord<String, Map<String, String>> record) {
//        Map<String, String> message = record.value();
//        if (message == null) {
//            log.warn("接收到空的系统消息");
//            return;
//        }
//        // 获取唯一的键值对
//        Map.Entry<String, String> entry = message.entrySet().iterator().next();
//        String targetUserId = entry.getKey();
//        String content = entry.getValue();
//
//        log.info("接收到系统消息通知: {}", message);
//
//    }

}
