package com.eshop.product.repository;

import com.eshop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    // custom query methods if needed
}
