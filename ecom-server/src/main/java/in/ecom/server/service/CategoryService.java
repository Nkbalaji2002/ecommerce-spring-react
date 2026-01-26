package in.ecom.server.service;

import in.ecom.server.payload.CategoryDTO;
import in.ecom.server.payload.CategoryResponse;
import jakarta.validation.Valid;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO delete(Long categoryId);

    CategoryDTO update(@Valid CategoryDTO category, Long categoryId);

}
