package com.example.user_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserEvent(String eventType, String email) {
        String message = eventType + ":" + email;
        kafkaTemplate.send("user-events", message);
        System.out.println("Отправлено сообщение в Kafka: " + message);
    }
}
