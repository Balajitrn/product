package com.eshop.product.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CategoryDTO {
    private Long id; // Optional for POST request
    private String name;
    private String description;

}
