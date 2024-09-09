package com.rjay.user.service;

import java.util.List;
import java.util.Optional;

import com.rjay.user.entity.Category;

public interface CategoryService {
	
	public List<Category> findAll();

	public Optional<Category> findById(Long id);

	public Category save(Category category);

	public void deleteById(Long id);
	
	Category addCategory(Category category);

	Category updateCategory(Category category);
	
    Category getCategoryById(long l);
	
	List<Category> getCategoriesByStatusIn(List<String> status);
}
