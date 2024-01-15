package com.eshop.product.repository;

import com.eshop.product.dto.ProductDTO;
import com.eshop.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    // custom query methods if needed

//    @Query("SELECT p FROM Product p WHERE " +
//            "(:searchTerm IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
//            "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
//            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
//            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
//    List<Product> searchProducts(
//            @Param("searchTerm") String searchTerm,
//            @Param("categoryId") Long categoryId,
//            @Param("minPrice") BigDecimal minPrice,
//            @Param("maxPrice") BigDecimal maxPrice
//    );
    List<Product> findByCategoryId(Long categoryId);

    List<Product> findProductsByNameAndCategoryId(String name, Long categoryId);


}
