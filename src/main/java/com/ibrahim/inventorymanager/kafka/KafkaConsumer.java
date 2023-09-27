package com.ibrahim.inventorymanager.kafka;


import com.ibrahim.inventorymanager.constants.AppConstants;
import com.ibrahim.inventorymanager.entities.inventory.Product;
import com.ibrahim.inventorymanager.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class KafkaConsumer {

    @Autowired
    private ProductRepository productRepository;


    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = AppConstants.INVENTORY_SAVE_NAME,
            groupId = AppConstants.GROUP_ID)
    public void saveProduct(Product product){


        productRepository.save(product);
        LOGGER.info(String.format("Product Saved into db -> %s", product));
    }


    @KafkaListener(topics = AppConstants.INVENTORY_UPDATE_NAME,
            groupId = AppConstants.GROUP_ID)
    public void updateProduct(Product product){

        productRepository.save(product);
        LOGGER.info(String.format("Product Saved into db -> %s", product));
    }


    @KafkaListener(topics = AppConstants.INVENTORY_DELETE_NAME,
            groupId = AppConstants.GROUP_ID)
    public void deleteProduct(Product product){

        productRepository.save(product);
        LOGGER.info(String.format("Product Saved into db -> %s", product));
    }



}
