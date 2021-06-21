package com.cg.osm.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.osm.entity.Product;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.error.ProductNotFoundException;
import com.cg.osm.repository.CategoryRepository;
import com.cg.osm.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	/*
	 * Injecting category and product repositories into service layer
	 */
	@Autowired
	private ProductRepository productRepository;
	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Product addProduct(Product product) throws CategoryNotFoundException {
		logger.info("Product addProduct()");
		int categoryId = product.getCategory().getCategoryId();
		boolean category_found = categoryRepository.existsById(categoryId);
		if (category_found) {
			return productRepository.save(product);
		} else {
			throw new CategoryNotFoundException("Invalid category");
		}
	}

	@Override
	public Product updateProduct(Product product) throws ProductNotFoundException, CategoryNotFoundException {
		logger.info("Product updateProduct()");

		int productId = product.getProdId();
		boolean product_found = productRepository.existsById(productId);
		int categoryId = product.getCategory().getCategoryId();
		boolean category_found = categoryRepository.existsById(categoryId);
		if (product_found) {
			if (category_found) {
				product.setProdName(product.getProdName());
				product.setProdPrice(product.getProdPrice());
				product.setExpDate(product.getExpDate());
				product.setCategory(product.getCategory());
				return productRepository.save(product);
			} else
				throw new CategoryNotFoundException("No such category found with id: " + categoryId);
		} else {
			throw new ProductNotFoundException("No such product found to update");
		}

	}

	@Override
	public String deleteProduct(int productId) throws ProductNotFoundException {
		logger.info("Product deleteProduct()");
		boolean product_found = productRepository.existsById(productId);
		if (product_found) {
			productRepository.deleteByid(productId);
			return "Product has been deleted";
		} else {
			throw new ProductNotFoundException("No such product found to delete with id: " + productId);
		}
	}

	@Override
	public List<Product> showAllProducts() throws ProductNotFoundException {
		logger.info("Product showAllProducts()");
		List<Product> productList = productRepository.findAll();
		if (productList.isEmpty()) {
			throw new ProductNotFoundException("No Products");
		} else {
			return productList;
		}
	}

	@Override
	public Optional<Product> findByProductId(int productId) throws ProductNotFoundException {
		logger.info("Product findByProductId()");
		Optional<Product> product = productRepository.findById(productId);
		if (product.isPresent()) {
			return product;
		} else {
			throw new ProductNotFoundException("No Product found with id: " + productId);
		}
	}

	@Override
	public List<Product> findByCategoryId(int categoryId) throws CategoryNotFoundException, ProductNotFoundException {
		logger.info("Product findByCategoryId()");
		boolean category_found = categoryRepository.existsById(categoryId);
		if (category_found) {
			List<Product> productList = productRepository.findByCategoryId(categoryId);
			return productList;
		} else {
			throw new CategoryNotFoundException("No such category found");
		}

	}
}