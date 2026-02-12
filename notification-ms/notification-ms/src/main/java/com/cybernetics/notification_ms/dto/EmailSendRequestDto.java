package com.cybernetics.notification_ms.dto;

public record EmailSendRequestDto(String email,
                                  String subject,
                                  String body) {

}
