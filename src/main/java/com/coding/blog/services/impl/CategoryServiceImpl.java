package com.coding.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding.blog.entities.Category;
import com.coding.blog.exceptions.ResourceNotFoundException;
import com.coding.blog.payloads.CategoryDto;
import com.coding.blog.repositories.CategoryRepo;
import com.coding.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	public CategoryRepo categoryRepo;

	@Autowired
	public ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category categoryName = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(categoryName);
		return this.modelMapper.map(addedCat, CategoryDto.class);

	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatedCat = this.categoryRepo.save(category);

		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category1 = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		this.categoryRepo.delete(category1);

	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {

		Category category2 = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		return this.modelMapper.map(category2, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();

		List<CategoryDto> categoryDtos = categories.stream()
				.map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

		return categoryDtos;
	}

}
