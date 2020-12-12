package com.devsuperior.dscatolog.tests.factory;

import java.util.ArrayList;
import java.util.List;

import com.devsuperior.dscatolog.entities.Category;

public class CategoryFactory {

	public static List<Category> createCategoryList(List<Long> list){
		List<Category> categories = new ArrayList<>();
		
		list.forEach(x -> categories.add(new Category(x, null)));
		
		return categories;
	}
}
