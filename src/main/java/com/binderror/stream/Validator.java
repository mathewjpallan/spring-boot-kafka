package com.binderror.stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Validator extends BaseStreamProcessor {

    @Value(value = "${message.topic.validator.output}")
    private String sinkTopic;

    @KafkaListener(id = "validator", groupId = "validator", topics = "${message.topic.validator.input}", containerFactory = "kafkaListenerContainerFactory")
    public void process(Map message) {
        System.out.println("Received Message : " + message);
        //validation logic should go here
        sendMessage(sinkTopic, message);
    }
}
