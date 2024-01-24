package com.eshop.product.repository;

import com.eshop.product.entity.Category;
import com.eshop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    // custom query per need

}
