package com.eshop.product.controller;

import com.eshop.product.dto.CategoryDTO;
import com.eshop.product.dto.ProductDTO;
import com.eshop.product.dto.SubCategoryDTO;
import com.eshop.product.entity.Category;
import com.eshop.product.entity.Product;
import com.eshop.product.service.CategoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.saveCategory(categoryDTO);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity <CategoryDTO> getCategoryById(@PathVariable Long categoryId) throws NotFoundException {
        CategoryDTO categoryDTO = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryDTO);
    }

    @GetMapping("/{categoryId}/subcategories")
    public List<SubCategoryDTO> getSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        return categoryService.findSubCategoriesByCategory(categoryId);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategoryList(){
        return new ResponseEntity<>(categoryService.getAllCategory(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@PathVariable Long id,@RequestBody CategoryDTO categoryDTO) throws NotFoundException {
        return new ResponseEntity<>(categoryService.updateCategoryById(id,categoryDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws NotFoundException {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // Additional endpoints as needed


}
