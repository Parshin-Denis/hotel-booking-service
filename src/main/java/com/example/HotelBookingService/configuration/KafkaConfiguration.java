package com.example.HotelBookingService.configuration;

import com.example.HotelBookingService.statistics.BookingMessage;
import com.example.HotelBookingService.statistics.UserMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.eventGroupId}")
    private String eventGroupId;

    @Bean
    public KafkaTemplate<String, UserMessage> userMessageKafkaTemplate(ObjectMapper objectMapper){
        ProducerFactory<String, UserMessage> userMessageProducerFactory =
                new DefaultKafkaProducerFactory<>(getProducerConfig(), new StringSerializer(),
                        new JsonSerializer<>(objectMapper));
        return new KafkaTemplate<>(userMessageProducerFactory);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserMessage> userMessageConcurrentKafkaListenerContainerFactory(
            ObjectMapper objectMapper) {
        ConsumerFactory<String, UserMessage> userMessageConsumerFactory =
                new DefaultKafkaConsumerFactory<>(getConsumerConfig(), new StringDeserializer(),
                        new JsonDeserializer<>(objectMapper));
        ConcurrentKafkaListenerContainerFactory<String, UserMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userMessageConsumerFactory);
        return factory;
    }

    @Bean
    public KafkaTemplate<String, BookingMessage> bookingMessageKafkaTemplate(ObjectMapper objectMapper) {
        ProducerFactory<String, BookingMessage> bookingMessageProducerFactory =
                new DefaultKafkaProducerFactory<>(getProducerConfig(), new StringSerializer(),
                        new JsonSerializer<>(objectMapper));
        return new KafkaTemplate<>(bookingMessageProducerFactory);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingMessage> bookingMessageConcurrentKafkaListenerContainerFactory(
            ObjectMapper objectMapper){
        ConsumerFactory<String, BookingMessage> bookingMessageConsumerFactory =
                new DefaultKafkaConsumerFactory<>(getConsumerConfig(), new StringDeserializer(),
                        new JsonDeserializer<>(objectMapper));
        ConcurrentKafkaListenerContainerFactory<String, BookingMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bookingMessageConsumerFactory);
        return factory;
    }

    private Map<String, Object> getProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }

    private Map<String, Object> getConsumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, eventGroupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return config;
    }
}
