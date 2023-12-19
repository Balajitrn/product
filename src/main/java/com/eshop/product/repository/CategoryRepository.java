package com.eshop.product.repository;

import com.eshop.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    // custom query per need
}
