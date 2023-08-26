package com.posting.category.service;

import com.posting.category.entity.Category;
import com.posting.category.repository.CategoryRepository;
import com.posting.content.entity.Content;
import com.posting.exception.BusinessLogicException;
import com.posting.exception.ExceptionCode;
import com.posting.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category){

        // JPA 전
//        Category createdCategory = category;
//        return createdCategory;

        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category){

        Category findCategory = findCategory(category.getCategoryId());

        Optional.ofNullable(category.getCategoryName())
                .ifPresent(categoryName -> findCategory.setCategoryName(categoryName));


        //JPA 전
//        Category updateCategory = category;
//        return updateCategory;

        return categoryRepository.save(findCategory);
    }

    public Category findCategory(long categoryId){

        // JPA 전
//        Category category = new Category(categoryId, "카테1");
//
//        // throw new RuntimeException("예외 던짐");
//        // throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND); // BusinessLogicException 클래스 사용
//        return category;

        Optional<Category> optionalCategory =
                categoryRepository.findById(categoryId);

        Category findCategory =
                optionalCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND));

        return findCategory;

    }

    // 추후 페이징 예정
    public List<Category> findCategories(){

        // JPA 전
//        List<Category> categories = List.of(
//                new Category(1L,"일반"),
//                new Category(2L, "게임")
//        );
//
//        return categories;

        return categoryRepository.findAll();

    }

    public void deleteCategory(long categoryId){

        Category findCategory = findCategory(categoryId);

        categoryRepository.delete(findCategory);

    }
}
