package com.ibrahim.inventorymanager.controllers;

import com.ibrahim.inventorymanager.controllers.InventoryController;
import com.ibrahim.inventorymanager.dtos.BaseResponse;
import com.ibrahim.inventorymanager.entities.inventory.Product;
import com.ibrahim.inventorymanager.services.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {
        Product product = new Product();
        when(inventoryService.saveProduct(product)).thenReturn(new ResponseEntity<>(new BaseResponse(true, "Product added"), HttpStatus.CREATED));

        ResponseEntity<BaseResponse> response = inventoryController.addProduct(product);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Product added", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        Long id = 1L;
        when(inventoryService.updateProduct(product, id)).thenReturn(new ResponseEntity<>(new BaseResponse(true, "Product updated"), HttpStatus.OK));

        ResponseEntity<BaseResponse> response = inventoryController.updateProduct(product, id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product updated", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void testDeleteProduct() {
        Long id = 1L;
        when(inventoryService.deleteProduct(id)).thenReturn(new ResponseEntity<>(new BaseResponse(true, "Product deleted"), HttpStatus.OK));

        ResponseEntity<BaseResponse> response = inventoryController.deleteProduct(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void testUploadExcelFile() {
        MultipartFile excelFile = mock(MultipartFile.class);
        when(inventoryService.parseAndSaveExcelFileContents(excelFile)).thenReturn(new ResponseEntity<>(new BaseResponse(true, "File uploaded"), HttpStatus.OK));

        ResponseEntity<BaseResponse> response = inventoryController.uploadExcelFile(excelFile);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded", Objects.requireNonNull(response.getBody()).getMessage());
    }
}
