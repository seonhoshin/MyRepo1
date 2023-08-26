package com.posting.category.mapper;

import com.posting.category.dto.CategoryResponseDto;
import com.posting.category.dto.CategoryPatchDto;
import com.posting.category.dto.CategoryPostDto;
import com.posting.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto);
    Category categoryPatchDtoToCategory(CategoryPatchDto categoryPatchDto);
    CategoryResponseDto categoryToCategoryResponseDto(Category category);

//    default Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto){
//
//        // 카테고리를 추가하는데 굳이 추가하는 내용에 content내용이 올까? 안올걸?
//        Category category = new Category();
//
//        category.setCategoryId(category.getCategoryId());
//
//
//    }

}
