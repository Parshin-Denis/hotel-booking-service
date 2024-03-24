package com.example.HotelBookingService.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final StatisticsService statisticsService;

    @KafkaListener(topics = "${app.kafka.userTopic}", groupId = "${app.kafka.eventGroupId}",
    containerFactory = "userMessageConcurrentKafkaListenerContainerFactory")
    public void listenUserMessage(@Payload UserMessage userMessage,
                                  @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        statisticsService.saveUserStatistics(userMessage, timestamp);
    }

    @KafkaListener(topics = "${app.kafka.bookingTopic}", groupId = "${app.kafka.eventGroupId}",
            containerFactory = "bookingMessageConcurrentKafkaListenerContainerFactory")
    public void listenBookingMessage(@Payload BookingMessage bookingMessage,
                                     @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        statisticsService.saveBookingStatistics(bookingMessage, timestamp);
    }
}
