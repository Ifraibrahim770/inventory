package com.ibrahim.inventorymanager.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibrahim.inventorymanager.entities.inventory.Product;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class ProductSerializer implements Serializer<Product> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration is needed for this serializer
    }

    @Override
    public byte[] serialize(String topic, Product product) {
        if (product == null) {
            return null;
        }
        try {
            // Serialize the Product object into a byte array using Jackson ObjectMapper
            return objectMapper.writeValueAsBytes(product);
        } catch (IOException e) {
            throw new RuntimeException("Error serializing product: " + e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        // Nothing to close for this serializer
    }
}
