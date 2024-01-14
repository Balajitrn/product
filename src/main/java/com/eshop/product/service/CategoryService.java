package com.eshop.product.service;

import com.eshop.product.dto.CategoryDTO;
import com.eshop.product.dto.SubCategoryDTO;
import com.eshop.product.entity.Category;
import com.eshop.product.entity.Product;
import com.eshop.product.entity.SubCategory;
import com.eshop.product.repository.CategoryRepository;
import com.eshop.product.repository.ProductRepository;
import com.eshop.product.repository.SubCategoryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository,SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Transactional
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return new CategoryDTO(savedCategory.getId(), savedCategory.getName(), savedCategory.getDescription());
    }


    @Transactional
    public SubCategoryDTO saveSubCategory(SubCategoryDTO subcategoryDTO ) throws NotFoundException {
      SubCategory subCategory = convertToSubCategoryEntity(subcategoryDTO);
      SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
      return convertToSubCategoryDTO(savedSubCategory);


    }
    @Transactional
    public List<CategoryDTO> getAllCategory(){
        return categoryRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<SubCategoryDTO> findSubCategoriesByCategory(Long parentCategoryId) {
        List<SubCategory> subCategories = subCategoryRepository.findBySubCategoryId(parentCategoryId);
        return subCategories.stream()
                .map(this::convertToSubCategoryDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public CategoryDTO updateCategoryById(Long id, CategoryDTO categoryDTO) throws NotFoundException {
        Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Category no found" + id));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return convertToDto(updatedCategory);
    }

    @Transactional
    public void deleteById(Long id) throws NotFoundException {
        if(!categoryRepository.existsById(id)){
            throw new NotFoundException("Category not found" + id);
        }
        categoryRepository.deleteById(id);
    }


    private CategoryDTO convertToDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
                        != null ? category.getDescription() : null);

    }

    private SubCategoryDTO convertToSubCategoryDTO(SubCategory subCategory) {
        return new SubCategoryDTO(
                subCategory.getId(),
                subCategory.getName(),
                subCategory.getSubCategory() != null ? subCategory.getSubCategory().getId() : null);
    }

    private SubCategory convertToSubCategoryEntity(SubCategoryDTO subCategoryDTO) throws NotFoundException {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryDTO.getName());
        if(subCategoryDTO.getParentCategoryId() !=null) {
            Category mainCategory = categoryRepository.findById(subCategoryDTO.getParentCategoryId())
                    .orElseThrow(()-> new NotFoundException("Category not found"));
            subCategory.setSubCategory(mainCategory);
        }
        else {
            throw new IllegalArgumentException("CategoryID is required");
        }

        return subCategory;

    }



    @Transactional
    public CategoryDTO getCategoryById(Long categoryId) throws NotFoundException {
        Optional <Category> category =  categoryRepository.findById(categoryId);
        if(category.isPresent()){
            return convertToDto(category.get());
        } else {
            throw new NotFoundException("Category not found with ID: "+ categoryId);
        }
    }






}
