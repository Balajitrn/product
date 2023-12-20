package com.eshop.product.controller;

import com.eshop.product.dto.ProductDTO;
import com.eshop.product.entity.Product;
import com.eshop.product.service.ProductService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
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

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) throws NotFoundException {
        ProductDTO productDTO = productService.getProductById(productId);
        return ResponseEntity.ok(productDTO);
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
