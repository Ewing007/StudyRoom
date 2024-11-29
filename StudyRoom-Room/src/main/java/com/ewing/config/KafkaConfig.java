package com.ewing.config;

import com.ewing.domain.dto.CheckInDto;
import constant.SystemConfigConstant;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ewing
 * @Date: 2024-11-22-13:01
 * @Description:
 */
@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic myTopic() {
        return new NewTopic(SystemConfigConstant.REPLY_MESSAGE_TOPIC, 1, (short) 1);
    }
    @Bean
    public NewTopic myTopic2() {
        return new NewTopic(SystemConfigConstant.SYSTEM_MESSAGE_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic myTopic3() {
        return new NewTopic(SystemConfigConstant.USER_CREDIT_DEDUCTION_TOPIC, 1, (short) 1);
    }


    @Bean
    public NewTopic myTopic4() {
        return new NewTopic(SystemConfigConstant.CHECK_IN_TOPIC, 1, (short) 1);
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, SystemConfigConstant.MESSAGE_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ewing.domain.dto");
        return props;
    }

    @Bean
    public ConsumerFactory<String, CheckInDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CheckInDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CheckInDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}