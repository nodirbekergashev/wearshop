package com.powerofwear.dto.product;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageUpdateDto {
    private Long id;
    private String url;
    private String altText;
    private boolean primary;
    private Integer position;
}