package com.eshop.product.repository;

import com.eshop.product.dto.ProductDTO;
import com.eshop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    // custom query methods if needed
    List<Product> findByCategoryId(Long categoryId);


}
