package com.eshop.product.service;

import com.eshop.product.dto.CategoryDTO;
import com.eshop.product.entity.Category;
import com.eshop.product.repository.CategoryRepository;
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

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
    public List<CategoryDTO> getAllCategory(){
        return categoryRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
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
        categoryRepository.deleteById(id) ;
    }

    private CategoryDTO convertToDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
                        != null ? category.getDescription() : null);
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
