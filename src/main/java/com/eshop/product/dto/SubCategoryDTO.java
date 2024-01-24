package com.eshop.product.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class SubCategoryDTO  {
    private Long id; // Optional for POST request
    private String name;
    private Long parentCategoryId;



}
