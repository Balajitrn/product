package com.eshop.product.service;

import com.eshop.product.dto.ProductDTO;
import com.eshop.product.dto.ReviewDTO;
import com.eshop.product.entity.Category;
import com.eshop.product.entity.Product;
import com.eshop.product.entity.Rating;
import com.eshop.product.entity.Review;
import com.eshop.product.repository.CategoryRepository;
import com.eshop.product.repository.ProductRepository;
import com.eshop.product.repository.RatingRepository;
import com.eshop.product.repository.ReviewRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final RatingRepository ratingRepository;

    private final ReviewRepository reviewRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,RatingRepository ratingRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * method to save the product
     * @param productDto
     * @return
     * @throws NotFoundException
     */
    @Transactional
    public ProductDTO saveProduct(ProductDTO productDto) throws NotFoundException {
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    /**
     * retreive the product by ID
     * @param productId
     * @return
     */
    public ProductDTO getProductById(Long productId) throws NotFoundException {
       Optional<Product> product =  productRepository.findById(productId);
        if(product.isPresent()){
            return convertToDto(product.get());
        } else {
            throw new NotFoundException("Product not found with ID: "+ productId);
        }
    }

    /**
     * search products based on category and product name
     *
     */


    /**
     * get all products
     * @return list of products
     */
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve list of products based on categoryId
     * @param categoryId
     */
    public  List<ProductDTO> findProductsByCategoryId(Long categoryId) throws NotFoundException {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if(products.isEmpty()) {
            throw new NotFoundException("Product not found");
        }
        else {
            return products.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
    }

    /**
     * method to update the product information
     * @param productId
     * @param productDTO
     * @return
     * @throws NotFoundException
     */
    @Transactional
    public ProductDTO updateProduct (Long productId, ProductDTO productDTO) throws NotFoundException {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        //update the product fileds
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        // handle category update if necessary
        if(productDTO.getCategoryId() != null){
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("category not found"));
            product.setCategory(category);
        }

        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    /**
     * delete the product
     * @param productId
     */

    @Transactional
    public void deleteProduct(Long productId) throws NotFoundException {
    if(!productRepository.existsById(productId)){
        throw new NotFoundException("Product not found with id "+productId);
    }
    productRepository.deleteById(productId);
    }

/**
 * rate a product
 * @param productId
 * @param ratingValue
 * void
 * throws NotFoundException
 */

    public void rateProduct(Long productId, double ratingValue){
    Product product = productRepository.findById(productId)
            .orElseThrow(()-> new RuntimeException("Product not found"));
    Rating rating = new Rating();
    rating.setRating(ratingValue);
    rating.setProduct(product);

    ratingRepository.save(rating);
}

/**
 * Review product
 *
 */

    public void  reviewProduct(Long productId, ReviewDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        Review review = new Review();
        review.setUsername(reviewDTO.getUsername());
        review.setComment(reviewDTO.getComment());
        review.setProduct(product);
        review.setCreatedDate(new Date());

        reviewRepository.save(review);
    }


   public List<ProductDTO> searchProductsByNameAndCategory(String name, Long categoryId) throws NotFoundException {
      List <Product> product = productRepository.findProductsByNameAndCategoryId(name, categoryId);
      if(product.isEmpty() ){
          throw new NotFoundException("Product not found");
      }
      else {
          return product.stream()
                  .map(this::convertToDto)
                  .collect(Collectors.toList());
      }

   }



    /**
     * method to convert the entity to DTO
     * @param product entity
     * @return product Dto
     */
    private ProductDTO convertToDto(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getId() : null);
         }

    private Product convertToEntity(ProductDTO productDto) throws NotFoundException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        if(productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            product.setCategory(category);
        } else {
            throw new IllegalArgumentException("Category ID is required");
        }

        return product;
    }
    //service method like saveProduct, getProduct, etc.
}
