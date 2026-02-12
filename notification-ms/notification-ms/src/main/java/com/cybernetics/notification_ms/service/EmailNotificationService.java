package com.cybernetics.notification_ms.service;

import com.cybernetics.notification_ms.dto.KafkaRequestDto;

public interface EmailNotificationService {

    void sendEmail(KafkaRequestDto kafkaRequestDto);

}
