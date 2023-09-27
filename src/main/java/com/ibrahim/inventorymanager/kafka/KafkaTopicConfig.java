package com.ibrahim.inventorymanager.kafka;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic inventorySaveTopic(){
        return TopicBuilder.name("inventory-topic")
                .build();
    }


    @Bean
    public NewTopic inventoryUpdateTopic(){
        return TopicBuilder.name("inventory-update")
                .build();
    }


    @Bean
    public NewTopic inventoryDeleteTopic(){
        return TopicBuilder.name("inventory-delete")
                .build();
    }



}
