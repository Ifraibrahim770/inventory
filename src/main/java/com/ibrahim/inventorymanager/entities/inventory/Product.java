package com.ibrahim.inventorymanager.entities.inventory;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private  Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_quantity")
    private int quantity;
    @Column(name = "product_unit_price")
    private String unitPrice;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean deleted;

}
