package com.ibrahim.inventorymanager.entities.inventory;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;


@Entity
@Table(name = "Products")
@Data

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private  Long id;
    @Column(name = "product_name")
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "quantity cannot be null")
    @Column(name = "product_quantity")
    private int quantity;
    private String unitPrice;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean deleted;

}
