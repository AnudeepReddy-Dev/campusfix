package com.campusfix.campusfix.repository;

import com.campusfix.campusfix.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}