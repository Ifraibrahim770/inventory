package com.ibrahim.inventorymanager.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibrahim.inventorymanager.entities.inventory.Product;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class ProductDeserializer implements Deserializer<Product> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public Product deserialize(String s, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            // Deserialize the byte array into a Product object using Jackson ObjectMapper
            return objectMapper.readValue(data, Product.class);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing product: " + e.getMessage(), e);
        }
    }

    @Override
    public Product deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
