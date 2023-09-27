package com.ibrahim.inventorymanager.controllers;


import com.ibrahim.inventorymanager.dtos.BaseResponse;
import com.ibrahim.inventorymanager.entities.inventory.Product;
import com.ibrahim.inventorymanager.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    @PostMapping("/addProduct")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BaseResponse> addProduct(@RequestBody Product product) {
        return inventoryService.saveProduct(product);

    }


    @PutMapping("/{id}/updateProduct")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BaseResponse> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        return inventoryService.updateProduct(product, id);


    }

    @DeleteMapping("/{id}/deleteProduct")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BaseResponse> deleteProduct(@PathVariable Long id) {
       return inventoryService.deleteProduct(id);


    }

    @PostMapping(path = "/uploadFile",consumes = {MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BaseResponse> uploadExcelFile(@RequestParam("file") MultipartFile excelFile) {

        return inventoryService.parseAndSaveExcelFileContents(excelFile);

    }
}
