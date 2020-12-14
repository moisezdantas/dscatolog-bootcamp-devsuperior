package com.devsuperior.dscatolog.tests.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.devsuperior.dscatolog.entities.Category;
import com.devsuperior.dscatolog.entities.Product;
import com.devsuperior.dscatolog.repositories.ProductRepository;
import com.devsuperior.dscatolog.tests.factory.CategoryFactory;
import com.devsuperior.dscatolog.tests.factory.ProductFactory;
import com.google.common.primitives.Longs;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;

	private long existindId;
	private long nonExistingId;
	private long counTotalProducts;
	private long countPCGamerProducts;
	private PageRequest pageRequest;
	private long countProductsCategoryThree;
	
	@BeforeEach
	void setup() throws Exception {
		existindId = 1L;
		nonExistingId = 1000L;
		counTotalProducts = 25L;
		countPCGamerProducts = 21L;
		countProductsCategoryThree= 23L;
		pageRequest = PageRequest.of(0, 10);
	}
	
	@Test
	public void findShouldReturnProductsWhenExistAllCategories() {
		
		List<Category> categories = CategoryFactory.createCategoryList(Longs.asList(1L,2L,3l));
		Page<Product> result = repository.find(categories, "", pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(counTotalProducts, result.getTotalElements());
		
	}
	
	@Test
	public void findShouldReturnProductsWhenExistCategoryThree() {
		
		List<Category> categories = CategoryFactory.createCategoryList(Longs.asList(3l));
		Page<Product> result = repository.find(categories, "", pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countProductsCategoryThree, result.getTotalElements());
		
	}
	
	@Test
	public void findShouldReturnNothingWhenNameDoesNotExist() {
		
		String name = "Camera";
		
		Page<Product> result = repository.find(null, name, pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
		
	}
	
	
	@Test
	public void findShouldReturnALLProductsWhenNameIsEmpty() {
		
		String name = "";
		
		Page<Product> result = repository.find(null, name, pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(counTotalProducts, result.getTotalElements());
		
	}
	
	@Test
	public void findShouldReturnProductsWhenNameExistsIgnoreCase() {
		
		String name = "pc gAMer";
		
		Page<Product> result = repository.find(null, name, pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
		
	}
	
	@Test
	public void findShouldReturnProductsWhenNameExists() {
		String name = "Pc Gamer";
		
		
		Page<Product> result = repository.find(null, name, pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
		
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		
		Product product = ProductFactory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Optional<Product> result = repository.findById(product.getId());
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(counTotalProducts + 1L, product.getId());
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(result.get(), product);
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExits() {
		repository.deleteById(existindId);
		Optional<Product> result = repository.findById(existindId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExits() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});

	}

}
