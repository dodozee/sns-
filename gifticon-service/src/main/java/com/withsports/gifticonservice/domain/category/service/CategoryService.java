package com.withsports.gifticonservice.domain.category.service;


import com.withsports.gifticonservice.domain.category.dto.CreateCategoryDto;

public interface CategoryService {
    Long createCategory(Long adminId, CreateCategoryDto createCategoryDto);
}
