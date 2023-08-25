package com.posting.category.dto;

import javax.validation.constraints.NotBlank;

public class CategoryPostDto {

    @NotBlank
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
