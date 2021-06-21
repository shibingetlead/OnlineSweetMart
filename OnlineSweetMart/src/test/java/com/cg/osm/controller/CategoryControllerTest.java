package com.cg.osm.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.cg.osm.entity.Category;
import com.cg.osm.error.CategoryNotFoundException;
import com.cg.osm.service.CategoryServiceImpl;
import com.cg.osm.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class )

 class CategoryControllerTest {
	@Autowired
	MockMvc mockmvc;

	@MockBean
	ProductServiceImpl service;
	@MockBean
	CategoryServiceImpl cservice;

	@Autowired
	ObjectMapper mapper;
	
	@Test
	void test_addCategory() throws Exception {
		Category c = new Category(1, "type1");
		BDDMockito.given(cservice.addCategory(c)).willReturn(c);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/category")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(c));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_deleteCategory() throws Exception {
		BDDMockito.given(cservice.deleteCategoryById(Mockito.anyInt())).willReturn("Category Deleted Successfully");
		mockmvc.perform(MockMvcRequestBuilders.delete("/category/1")).andExpect(status().isOk());
	}

	@Test
	void test_deleteCategory_ThrowCustomerNotFoundException() throws Exception {
		BDDMockito.given(cservice.deleteCategoryById(Mockito.anyInt()))
				.willThrow(new CategoryNotFoundException("category not found"));
		mockmvc.perform(MockMvcRequestBuilders.delete("/customer/101")).andExpect(status().isNotFound());
	}

	@Test
	void test_showAllCategories() throws Exception {
		Category c = new Category(1, "type1");
		Category c1 = new Category(2, "type2");
		List<Category> categorylist = new ArrayList<>();
		categorylist.add(c);
		categorylist.add(c1);
		BDDMockito.given(cservice.showAllCategories()).willReturn(categorylist);
		mockmvc.perform(MockMvcRequestBuilders.get("/category")).andExpect(status().isOk());
	}

	@Test
	void test_showAllCategories_ThrowCategoryNotFountException() throws Exception {
		BDDMockito.given(cservice.showAllCategories()).willThrow(new CategoryNotFoundException("No category"));
		mockmvc.perform(MockMvcRequestBuilders.get("/category")).andExpect(status().isNotFound());
	}

	@Test
	void test_updateCategory() throws Exception {
		Category c = new Category(1, "type1");

		BDDMockito.given(cservice.updateCategory(c)).willReturn(c);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/category")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(c));

		mockmvc.perform(builder).andExpect(status().isOk());
	}

	@Test
	void test_updateCategory_ThrowCategoryNotFoundException() throws Exception {
		Category c = new Category(1, "type1");
		BDDMockito.given(cservice.updateCategory(Mockito.any(Category.class)))
				.willThrow(new CategoryNotFoundException("category not found"));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/category")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(c));

		mockmvc.perform(builder).andExpect(status().isNotFound());
	}

	@Test
	void findProductByIdTest() throws Exception {
		Category c = new Category(1, "type1");

		BDDMockito.given(cservice.findByCategoryId(1)).willReturn(Optional.of(c));
		mockmvc.perform(MockMvcRequestBuilders.get("/category/1")).andExpect(status().isOk());
	}

	@Test
	void findCategoryByIdTest_ThrowCategoryNotFoundException() throws Exception {
		BDDMockito.given(cservice.findByCategoryId(Mockito.anyInt()))
				.willThrow(new CategoryNotFoundException("category not found"));
		mockmvc.perform(MockMvcRequestBuilders.get("/category/1")).andExpect(status().isNotFound());
	}


}
