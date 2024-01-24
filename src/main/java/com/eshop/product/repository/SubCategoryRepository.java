package com.eshop.product.repository;

import com.eshop.product.entity.Category;
import com.eshop.product.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    public List<SubCategory> findBySubCategoryId(Long parentCategoryId);
}
