package com.devsuperior.dscatolog.tests.factory;

import java.time.Instant;

import com.devsuperior.dscatolog.dto.ProductDTO;
import com.devsuperior.dscatolog.entities.Product;
import com.google.common.primitives.Longs;

public class ProductFactory {

	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "http://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().addAll(CategoryFactory.createCategoryList(Longs.asList(1L)));
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static ProductDTO createProductDTO(Long id) {
		ProductDTO dto = createProductDTO();
		dto.setId(id);
		return dto;

	}
	
}
