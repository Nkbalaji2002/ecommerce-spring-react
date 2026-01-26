package in.ecom.server.service;

import in.ecom.server.model.Category;
import in.ecom.server.payload.CategoryDTO;
import in.ecom.server.payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    String delete(Long categoryId);

    Category update(Category category, Long categoryId);

}
