package com.withsports.gifticonservice.domain.category.service;

import com.withsports.gifticonservice.domain.category.dto.CreateCategoryDto;
import com.withsports.gifticonservice.domain.category.entity.Category;
import com.withsports.gifticonservice.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Long createCategory(Long adminId, CreateCategoryDto createCategoryDto) {

        Category category = Category.of(createCategoryDto.getCategoryName(), createCategoryDto.getOrder());
        Category newCategory = categoryRepository.save(category);

        return newCategory.getId();

    }
}
