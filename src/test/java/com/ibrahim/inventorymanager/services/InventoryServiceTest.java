package com.ibrahim.inventorymanager.services;

import com.ibrahim.inventorymanager.dtos.BaseResponse;
import com.ibrahim.inventorymanager.entities.inventory.Product;
import com.ibrahim.inventorymanager.kafka.KafkaProducer;
import com.ibrahim.inventorymanager.repositories.ProductRepository;
import com.ibrahim.inventorymanager.services.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private KafkaProducer producer;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        inventoryService.addProduct(product);
        verify(producer, times(1)).saveProduct(product);
    }

    @Test
    public void testUpdateProduct() {
        Product newProductDetails = new Product();
        Long id = 1L;
        Product productToBeUpdated = new Product();
        productToBeUpdated.setId((long) Math.toIntExact(id));
        when(productRepository.findById(Math.toIntExact(id))).thenReturn(Optional.of(productToBeUpdated));

        ResponseEntity<BaseResponse> response = inventoryService.updateProduct(newProductDetails, id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product has been updated successfully", response.getBody().getMessage());
        verify(producer, times(1)).updateProduct(productToBeUpdated);
    }

    @Test
    public void testUpdateProductNotFound() {
        Long id = 1L;
        when(productRepository.findById(Math.toIntExact(id))).thenReturn(Optional.empty());

        ResponseEntity<BaseResponse> response = inventoryService.updateProduct(new Product(), id);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Product does not exist", response.getBody().getMessage());
        verify(producer, never()).updateProduct(any());
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;
        Product productToBeDeleted = new Product();
        productToBeDeleted.setId((long) Math.toIntExact(id));
        when(productRepository.findById(Math.toIntExact(id))).thenReturn(Optional.of(productToBeDeleted));

        ResponseEntity<BaseResponse> response = inventoryService.deleteProduct(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully", response.getBody().getMessage());
        assertTrue(productToBeDeleted.isDeleted());
        verify(producer, times(1)).updateProduct(productToBeDeleted);
    }

    @Test
    public void testDeleteProductNotFound() {
        Long id = 1L;
        when(productRepository.findById(Math.toIntExact(id))).thenReturn(Optional.empty());

        ResponseEntity<BaseResponse> response = inventoryService.deleteProduct(id);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Product does not exist", response.getBody().getMessage());
        verify(producer, never()).updateProduct(any());
    }

    @Test
    public void testParseExcelFile() throws IOException {
        MockMultipartFile excelFile = new MockMultipartFile("filename", "someTestFile.xlsx", "application/vnd.ms-excel", new ClassPathResource("someTestFile.xlsx").getInputStream());
        List<Product> products = inventoryService.parseExcelFile(excelFile);

        assertNotNull(products);

    }

    @Test
    public void testParseAndSaveExcelFileContents() throws IOException {
        MockMultipartFile excelFile = new MockMultipartFile("filename", "someTestFile.xlsx", "application/vnd.ms-excel", new ClassPathResource("someTestFile.xlsx").getInputStream());
        ResponseEntity<BaseResponse> response = inventoryService.parseAndSaveExcelFileContents(excelFile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Excel has been uploaded and processed successfully", response.getBody().getMessage());
        verify(producer, times(3)).saveProduct(any(Product.class));
    }


}
