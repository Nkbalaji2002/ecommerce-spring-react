package in.ecom.server.service;

import in.ecom.server.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(Category category);

    String delete(Long categoryId);

    Category update(Category category, Long categoryId);

}
