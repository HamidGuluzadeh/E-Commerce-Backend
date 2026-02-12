package com.cybernetics.notification_ms.dto;

public record KafkaRequestDto(String email,
                              String orderId,
                              String username) {

}
