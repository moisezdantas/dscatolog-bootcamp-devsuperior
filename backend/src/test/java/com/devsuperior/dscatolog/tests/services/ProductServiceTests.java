package com.devsuperior.dscatolog.tests.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatolog.dto.ProductDTO;
import com.devsuperior.dscatolog.entities.Category;
import com.devsuperior.dscatolog.entities.Product;
import com.devsuperior.dscatolog.repositories.CategoryRepository;
import com.devsuperior.dscatolog.repositories.ProductRepository;
import com.devsuperior.dscatolog.services.ProductService;
import com.devsuperior.dscatolog.services.execeptions.DataBaseException;
import com.devsuperior.dscatolog.services.execeptions.ResourceNotFoundException;
import com.devsuperior.dscatolog.tests.factory.CategoryFactory;
import com.devsuperior.dscatolog.tests.factory.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private Product product;
	private ProductDTO productDTO;
	private PageImpl<Product> page;
	private Category category;
	private Long categoryId;

	@BeforeEach
	public void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		product = ProductFactory.createProduct();
		productDTO = ProductFactory.createProductDTO();
		page = new PageImpl<>(List.of(product));
		categoryId = 1L;
		category = CategoryFactory.createCategory();

		// find all
		Mockito.when(repository.find(ArgumentMatchers.any(), ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(page);

		// save
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

		// find by id
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// Delete
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		//update
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		Mockito.when(categoryRepository.getOne(categoryId)).thenReturn(category);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingId);
		
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, productDTO);
		});

		Mockito.verify(repository, Mockito.times(1)).getOne(nonExistingId);
	}
	
	@Test
	public void updateShouldReturnProductoWhenIdExist() {
		
		ProductDTO newDto = service.update(existingId, productDTO);

		Assertions.assertEquals(productDTO.getId(), newDto.getId());
		Assertions.assertEquals(productDTO.getName(), newDto.getName());

		Mockito.verify(repository, Mockito.timeout(1)).save(any());
	}
	
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);

	}

	@Test
	public void findByIdShouldReturnProductWhenIdExist() {

		ProductDTO dto = service.findById(existingId);

		Assertions.assertEquals(product.getId(), dto.getId());

		Mockito.verify(repository, Mockito.timeout(1)).findById(existingId);

	}

	@Test
	public void findAllPagedShouldReturnProductPaged() {

		Page<ProductDTO> pages = service.findAllPaged(anyLong(), anyString(), any());

		Assertions.assertEquals(1L, pages.getTotalElements());

		Mockito.verify(repository, Mockito.timeout(1)).find(any(), anyString(), any());
	}

	@Test
	public void deleteShouldThrowDataBaseExceptionWhenDependentId() {

		Assertions.assertThrows(DataBaseException.class, () -> {
			service.delete(dependentId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
}
