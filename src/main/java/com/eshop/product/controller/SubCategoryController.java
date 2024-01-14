package com.eshop.product.controller;

import com.eshop.product.dto.SubCategoryDTO;
import com.eshop.product.service.CategoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {

    private final CategoryService subCategoryService;

    @Autowired
    public SubCategoryController(CategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubCategoryDTO createSubCategory(@RequestBody SubCategoryDTO subCategoryDTO) throws NotFoundException {
        return subCategoryService.saveSubCategory(subCategoryDTO);
    }



}
