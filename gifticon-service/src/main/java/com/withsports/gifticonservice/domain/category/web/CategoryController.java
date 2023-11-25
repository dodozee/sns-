package com.withsports.gifticonservice.domain.category.web;

import com.withsports.gifticonservice.domain.category.dto.CreateCategoryDto;
import com.withsports.gifticonservice.domain.category.service.CategoryService;
import com.withsports.gifticonservice.domain.category.web.request.CreateCategoryRequest;
import com.withsports.gifticonservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    ResponseEntity<Result> createCategory(@RequestHeader("user-id") String adminId,
                                          @RequestBody CreateCategoryRequest createCategoryRequest) {
        CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .categoryName(createCategoryRequest.getCategoryName())
                .order(createCategoryRequest.getOrder())
                .build();
        Long categoryId = categoryService.createCategory(Long.valueOf(adminId), createCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.createSuccessResult(categoryId));
    }

//    @GetMapping("/categorys")
//    ResponseEntity<Result> getCategorys(@RequestHeader("user-id") String adminId) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(Result.createSuccessResult(categoryService.getCategorys()));
//    }

}
