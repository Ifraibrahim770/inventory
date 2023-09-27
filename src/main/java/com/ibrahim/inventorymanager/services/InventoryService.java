package com.ibrahim.inventorymanager.services;

import com.ibrahim.inventorymanager.dtos.BaseResponse;
import com.ibrahim.inventorymanager.kafka.KafkaProducer;
import com.ibrahim.inventorymanager.entities.inventory.Product;
import com.ibrahim.inventorymanager.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class InventoryService {


    @Autowired
    private KafkaProducer producer;

    @Autowired
    private ProductRepository productRepository;

    public InventoryService(KafkaProducer kafkaProducer) {
        this.producer = kafkaProducer;
    }


    public void addProduct(Product product){
        producer.saveProduct(product);

    }


    public ResponseEntity<BaseResponse> updateProduct(Product newProductDetails, Long id){
        Product productToBeUpdated = productRepository.findById(Math.toIntExact(id)).orElse(null);
        if(productToBeUpdated == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Product does not exist"));
        }

        productToBeUpdated.setQuantity(newProductDetails.getQuantity());
        productToBeUpdated.setUnitPrice(newProductDetails.getUnitPrice());
        productToBeUpdated.setName(newProductDetails.getName());

        producer.updateProduct(productToBeUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(true, "Product has been updated successfully"));

    }

    public ResponseEntity<BaseResponse> deleteProduct( Long id){
        Product productToBeDeleted = productRepository.findById(Math.toIntExact(id)).orElse(null);
        if(productToBeDeleted == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Product does not exist"));
        }

       productToBeDeleted.setDeleted(true);
        producer.updateProduct(productToBeDeleted);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(true, "Product deleted successfully"));

    }

    public List<Product> parseExcelFile(MultipartFile excelFile) throws IOException {
        List<Product> products = new ArrayList<>();

        try (InputStream inputStream = excelFile.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream); // Use XSSFWorkbook for .xlsx files

            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip the header row
                }

                Product product = new Product();
                product.setName(row.getCell(0).getStringCellValue());
                product.setQuantity((int) row.getCell(1).getNumericCellValue());
                product.setUnitPrice(row.getCell(2).getStringCellValue());

                products.add(product);
            }

            workbook.close();
        }

        return products;
    }

    public ResponseEntity<BaseResponse> parseAndSaveExcelFileContents(MultipartFile excelFile) {
        try {
            List<Product> products = parseExcelFile(excelFile);
            for(Product product : products){
                saveProduct(product);
            }
            return ResponseEntity.ok(new BaseResponse(true, "Excel has been uploaded and processed successfully"));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new BaseResponse(false, "Failed to upload the file: " + e.getMessage()));
        }
    }

    public ResponseEntity<BaseResponse> saveProduct(Product product) {
        addProduct(product);
        return ResponseEntity.ok().body(new BaseResponse(true, "Product Saved Successfully"));
    }
}

