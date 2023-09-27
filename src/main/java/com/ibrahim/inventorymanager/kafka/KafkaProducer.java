package com.ibrahim.inventorymanager.kafka;

import com.ibrahim.inventorymanager.constants.AppConstants;
import com.ibrahim.inventorymanager.entities.inventory.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(org.apache.kafka.clients.producer.KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    public void saveProduct(Product product){
        LOGGER.info(String.format("Message sent -> %s", product.toString()));
        kafkaTemplate.send(AppConstants.INVENTORY_SAVE_NAME, product);
    }

    public void updateProduct(Product product){
        LOGGER.info(String.format("Message sent -> %s", product.toString()));
        kafkaTemplate.send(AppConstants.INVENTORY_UPDATE_NAME, product);
    }

}
