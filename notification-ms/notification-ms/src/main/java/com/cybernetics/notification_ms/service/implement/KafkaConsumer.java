package com.cybernetics.notification_ms.service.implement;

import com.cybernetics.notification_ms.dto.KafkaRequestDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KafkaConsumer {
    EmailNotificationServiceImpl emailNotificationService;
    KafkaTemplate<String, KafkaRequestDto> kafkaTemplate;

    @KafkaListener(topics = "kafka-payment-topic",
            groupId = "notification-group")
    public void consume(KafkaRequestDto kafkaRequestDto,
                        Acknowledgment ack) {
        log.info("Received message: {}", kafkaRequestDto);
        emailNotificationService.sendEmail(kafkaRequestDto);
        log.info("Message sent successfully!");
        ack.acknowledge();
    }
}
