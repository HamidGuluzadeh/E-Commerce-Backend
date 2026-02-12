package com.cybernetics.notification_ms.service.implement;

import com.cybernetics.notification_ms.dto.EmailSendRequestDto;
import com.cybernetics.notification_ms.dto.KafkaRequestDto;
import com.cybernetics.notification_ms.service.EmailNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailNotificationServiceImpl implements EmailNotificationService {
    final JavaMailSender javaMailSender;
    EmailSendRequestDto emailSendRequestDto;

    @Override
    public void sendEmail(KafkaRequestDto kafkaRequestDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(kafkaRequestDto.email());
        simpleMailMessage.setSubject("Successful Purchase");
        simpleMailMessage.setText("Hello, " + kafkaRequestDto.username() + "!\n\n" +
                "Your order has been approved!\n" +
                "Order ID: " + kafkaRequestDto.orderId()
        );

        javaMailSender.send(simpleMailMessage);
    }
}
