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

import com.cg.osm.entity.Product;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.error.ProductNotFoundException;
import com.cg.osm.service.ProductServiceImpl;

@RestController
public class ProductController {

	/*
	 * Injecting Product Service
	 */
	@Autowired
	private ProductServiceImpl productservice;
	Logger logger = LoggerFactory.getLogger(ProductController.class);

	/*
	 * Retrieving all the products throw exception if there's none
	 */
	@GetMapping(path = "/product")
	public List<Product> showProducts() throws ProductNotFoundException {
		logger.info("Product showProducts()");
		return productservice.showAllProducts();
	}

	/*
	 * Adding a product with validations throw exception when the category mentioned
	 * in the Body of product doesn't exist
	 */
	@PostMapping(path = "/product")
	public Product addProduct(@Valid @RequestBody Product product) throws CategoryNotFoundException {
		logger.info("Product addProduct()");
		return productservice.addProduct(product);
	}

	/*
	 * Updating an already existing product, if the product doesn't exist an
	 * exception is thrown
	 */
	@PutMapping(path = "/product")
	public Product updateProduct(@Valid @RequestBody Product product)
			throws ProductNotFoundException, CategoryNotFoundException {
		logger.info("Product updateProduct()");
		return productservice.updateProduct(product);
	}

	/*
	 * deleting a product from the database based on its ID throw exception if
	 * there's no such product present
	 */
	@DeleteMapping(path = "/product/{productId}")
	public String deleteProduct(@PathVariable("productId") int productId) throws ProductNotFoundException {
		logger.info("Product deleteProduct()");
		return productservice.deleteProduct(productId);
	}

	/*
	 * Finding a product based on productID throw exception if the product is not
	 * present
	 */
	@GetMapping(path = "/product/{productId}")
	public Product getProductById(@PathVariable("productId") int productId) throws ProductNotFoundException {
		Optional<Product> product = productservice.findByProductId(productId);
		logger.info("Product getProductById()");
		return product.get();
	}

	/*
	 * Finding all products based on categoryID throw exception if the category is
	 * not found and product not found exception if there are no products in the
	 * given category
	 */
	@GetMapping(path = "/product/category/{id}")
	public List<Product> showProductsOnCategory(@PathVariable("id") int categoryId)
			throws ProductNotFoundException, CategoryNotFoundException {
		logger.info("Product showProductsOnCategory()");
		return productservice.findByCategoryId(categoryId);
	}
}
