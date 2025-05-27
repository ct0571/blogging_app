package com.coding.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
