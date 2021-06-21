package com.cg.osm.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.osm.entity.Category;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Override
	public Category addCategory(Category category) {
		logger.info("Category addCategory()");

		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Category category) throws CategoryNotFoundException {
		logger.info("Category updateCategory()");
		int categoryId = category.getCategoryId();
		boolean found = categoryRepository.existsById(categoryId);
		if (found) {
			category.setName(category.getName());
			return categoryRepository.save(category);
		} else
			throw new CategoryNotFoundException("No such category found");

	}

	@Override
	public String deleteCategoryById(int categoryId) throws CategoryNotFoundException {
		logger.info("Category deleteCategoryById()");
		if (categoryRepository.existsById(categoryId)) {
			categoryRepository.deleteById(categoryId);
			return "Category deleted";
		} else
			throw new CategoryNotFoundException("No such category found with id: " + categoryId);

	}

	@Override
	public List<Category> showAllCategories() throws CategoryNotFoundException {
		logger.info("Category showAllCategories()");

		List<Category> categoryList = categoryRepository.findAll();
		if (categoryList.isEmpty())
			throw new CategoryNotFoundException("No Categories found");
		else
			return categoryList;
	}

	@Override
	public Optional<Category> findByCategoryId(int categoryId) throws CategoryNotFoundException {
		logger.info("Category getCategoryById()");
		Optional<Category> category = categoryRepository.findById(categoryId);
		if (category.isPresent()) {
			return category;
		} else
			throw new CategoryNotFoundException("No such category found with id: " + categoryId);

	}

}