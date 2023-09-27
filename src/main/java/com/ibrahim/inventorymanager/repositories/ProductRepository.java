package com.ibrahim.inventorymanager.repositories;

import com.ibrahim.inventorymanager.entities.inventory.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
