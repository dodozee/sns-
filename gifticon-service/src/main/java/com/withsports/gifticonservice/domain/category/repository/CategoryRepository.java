package com.withsports.gifticonservice.domain.category.repository;

import com.withsports.gifticonservice.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
