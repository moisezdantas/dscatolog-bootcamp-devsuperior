package com.devsuperior.dscatolog.tests.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatolog.dto.ProductDTO;
import com.devsuperior.dscatolog.services.ProductService;
import com.devsuperior.dscatolog.services.execeptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServeiceIT {

	@Autowired
	private ProductService service;
	
	private Long existingId;
	private Long nonExistingId;
	private long counTotalProducts;
	private long countPCGamerProducts;
	private PageRequest pageRequest;

	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		counTotalProducts = 25L;
		countPCGamerProducts = 21L;
		pageRequest = PageRequest.of(0, 10);
	}
	
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);	
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);	
		});
	}
	
	@Test
	public void findAllPagedShouldReturnNothingWhenNameDoesNotExist() {
		
		String name = "Camera";
		Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
		Assertions.assertTrue(result.isEmpty());
	}
	
	
	@Test
	public void findAllPagedShouldReturnALLProductsWhenNameIsEmpty() {
		
		String name = "";
		Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(counTotalProducts, result.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnProductsWhenNameExistsIgnoreCase() {
		
		String name = "pc gAMer";
		Page<ProductDTO> result = service.findAllPaged(0L, name, pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(countPCGamerProducts, result.getTotalElements());
		
	}
}
