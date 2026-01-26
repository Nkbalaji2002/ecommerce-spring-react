package in.ecom.server.service;

import in.ecom.server.model.Category;
import in.ecom.server.payload.CategoryDTO;
import in.ecom.server.payload.CategoryResponse;
import jakarta.validation.Valid;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    String delete(Long categoryId);

    CategoryDTO update(@Valid CategoryDTO category, Long categoryId);

}
