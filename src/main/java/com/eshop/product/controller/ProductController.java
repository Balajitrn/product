package com.eshop.product.controller;

import com.eshop.product.dto.ProductDTO;
import com.eshop.product.dto.ReviewDTO;
import com.eshop.product.entity.Product;
import com.eshop.product.service.ProductService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000") // React app's URL
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDto) throws NotFoundException {
        return productService.saveProduct(productDto);
    }


    @PostMapping("/{productId}/rating")
    public ResponseEntity<String> rateProduct(@RequestParam("ratingValue") double ratingValue, @PathVariable("productId") Long
                                              productId)  {
        productService.rateProduct(productId,ratingValue);
        return new ResponseEntity<>("Product rated successfully", HttpStatus.CREATED);
    }

    @PostMapping("/{productId}/review")
    public ResponseEntity<String> UserReview(@PathVariable Long productId, @RequestBody ReviewDTO reviewDTO) {
        productService.reviewProduct(productId,reviewDTO);
        return new ResponseEntity<>("Review submitted successfully",HttpStatus.CREATED);
    }



    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) throws NotFoundException {
        ProductDTO productDTO = productService.getProductById(productId);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ProductDTO>> featuredProducts() {
        List<ProductDTO> popularProducts = productService.getFeaturedProducts();
        return ResponseEntity.ok(popularProducts);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDTO> getProductsByCategory(@PathVariable Long categoryId) throws NotFoundException{
        return productService.findProductsByCategoryId(categoryId);
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam String name, @RequestParam Long categoryId) throws NotFoundException {
        return productService.searchProductsByNameAndCategory(name, categoryId);
    }





    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct (@PathVariable Long productId, @RequestBody ProductDTO productDTO) throws NotFoundException {
        ProductDTO updatedProduct = productService.updateProduct(productId,productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct (@PathVariable Long productId) throws NotFoundException {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

}
