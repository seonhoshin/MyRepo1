package com.posting.category.controller;

import com.posting.category.dto.CategoryPatchDto;
import com.posting.category.dto.CategoryPostDto;
import com.posting.category.dto.CategoryResponseDto;
import com.posting.category.entity.Category;
import com.posting.category.mapper.CategoryMapper;
import com.posting.category.service.CategoryService;
import com.posting.response.ErrorResponse;
import com.posting.utils.UriCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/category")
@Validated
public class CategoryController {

    private final static String CATEGORY_DEFAULT_URL = "/v1/category";

    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    public CategoryController(CategoryService categoryService, CategoryMapper mapper){
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postCategory(@Valid @RequestBody CategoryPostDto requestBody){

//        // mapper 역할
//        Category category = new Category();
//        category.setCategoryName(requestBody.getCategoryName());

        Category categorymapper = mapper.categoryPostDtoToCategory(requestBody);

        Category category = categoryService.createCategory(categorymapper);

        URI location = UriCreator.createUri(CATEGORY_DEFAULT_URL, category.getCategoryId());

        // return new ResponseEntity<>(response,HttpStatus.CREATED); mapper역할이 없을 경우 이거였음.
            // repository 이후 삭제... POST 이후 URI 보내야 함.
//        return new ResponseEntity<>(
//                mapper.categoryToCategoryResponseDto(response)
//                , HttpStatus.CREATED);

        return ResponseEntity.created(location).build();

    }

    @PatchMapping("/{category-id}")
    public ResponseEntity patchCategory(@PathVariable("category-id") @Positive long categoryId,
                                        @Valid @RequestBody CategoryPatchDto requestBody){

        requestBody.setCategoryId(categoryId);

//        // mapper 역할
//        Category category = new Category();
//        category.setCategoryId(requestBody.getCategoryId());
//        category.setCategoryName(requestBody.getCategoryName());

        Category category = mapper.categoryPatchDtoToCategory(requestBody);


        Category response = categoryService.updateCategory(category);

        return new ResponseEntity<>(
                mapper.categoryToCategoryResponseDto(response)
                , HttpStatus.OK);
    }

    @GetMapping("/{category-id}")
    public ResponseEntity getCategory(@PathVariable("category-id") @Positive  long categoryId){

        Category response = categoryService.findCategory(categoryId);

        return new ResponseEntity<>(
                mapper.categoryToCategoryResponseDto(response)
                , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCategories(){

        List<Category> categories = categoryService.findCategories();

        List<CategoryResponseDto> response =
                categories.stream()
                        .map(category -> mapper.categoryToCategoryResponseDto(category))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity deleteCategory(@PathVariable("category-id") @Positive long categoryId){

        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @ExceptionHandler
//    public ResponseEntity handleException(MethodArgumentNotValidException e){
//
//        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//
//        List<ErrorResponse.FieldError> errors =
//                fieldErrors.stream()
//                        .map(error -> new ErrorResponse.FieldError(
//                                error.getField(),
//                                error.getRejectedValue(),
//                                error.getDefaultMessage()
//                        ))
//                        .collect(Collectors.toList());
//
//        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);

//    }
}
