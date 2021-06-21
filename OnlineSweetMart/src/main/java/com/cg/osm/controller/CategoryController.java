package com.cg.osm.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.osm.entity.Category;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.service.CategoryServiceImpl;

@RestController
public class CategoryController {

	/*
	 * Injecting Category Service
	 */
	@Autowired
	private CategoryServiceImpl categoryservice;
	Logger logger = LoggerFactory.getLogger(CategoryController.class);

	/*
	 * Retrieving all the categories throw exception if there are no categories
	 */
	@GetMapping(path = "/category")
	public List<Category> showAllCategories() throws CategoryNotFoundException {
		logger.info("Category showAllCategories()");
		return categoryservice.showAllCategories();
	}

	/*
	 * Updating a category Throw exception if there is no such category
	 */
	@PutMapping(path = "/category")
	public Category updateCategory(@Valid @RequestBody Category category) throws CategoryNotFoundException {
		logger.info("Category updateCategory()");
		return categoryservice.updateCategory(category);
	}

	/*
	 * Adding a new category
	 */
	@PostMapping(path = "/category")
	public Category addCategory(@Valid @RequestBody Category category) {
		logger.info("Category addCategory()");
		return categoryservice.addCategory(category);
	}

	/*
	 * Deleting a category Throw exception if there is no such category
	 */
	@DeleteMapping(path = "/category/{categoryId}")
	public String deleteCategoryById(@PathVariable("categoryId") int id) throws CategoryNotFoundException {
		logger.info("Category deleteCategoryById()");
		return categoryservice.deleteCategoryById(id);

	}

	/*
	 * Getting a category based on its ID Throw exception if there is no such
	 * category
	 */
	@GetMapping(path = "/category/{categoryId}")
	public Category getCategoryById(@PathVariable("categoryId") int id) throws CategoryNotFoundException {
		Optional<Category> category = categoryservice.findByCategoryId(id);
		logger.info("Category getCategoryById()");
		return category.get();
	}

}
