package com.eshop.product.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private String username;
    private String comment;
}
