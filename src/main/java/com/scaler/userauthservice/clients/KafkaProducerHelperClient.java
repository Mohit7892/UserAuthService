package com.scaler.userauthservice.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerHelperClient {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String messageContent){
        //send the event/message to kafka topic
        kafkaTemplate.send(topic, messageContent);
        System.out.println("Message is sent to kakfa topic with "+topic+
                " and message "+messageContent);
    }
}
