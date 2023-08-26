package com.posting.category.mapper;

import com.posting.category.dto.CategoryPatchDto;
import com.posting.category.dto.CategoryPostDto;
import com.posting.category.dto.CategoryResponseDto;
import com.posting.category.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-26T10:14:23+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto) {
        if ( categoryPostDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setCategoryName( categoryPostDto.getCategoryName() );

        return category;
    }

    @Override
    public Category categoryPatchDtoToCategory(CategoryPatchDto categoryPatchDto) {
        if ( categoryPatchDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setCategoryName( categoryPatchDto.getCategoryName() );
        category.setCategoryId( categoryPatchDto.getCategoryId() );

        return category;
    }

    @Override
    public CategoryResponseDto categoryToCategoryResponseDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

        categoryResponseDto.setCategoryId( category.getCategoryId() );
        categoryResponseDto.setCategoryName( category.getCategoryName() );

        return categoryResponseDto;
    }
}
