package in.ecom.server.service.impl;

import in.ecom.server.exceptions.APIException;
import in.ecom.server.exceptions.ResourceNotFoundException;
import in.ecom.server.model.Category;
import in.ecom.server.repository.CategoryRepository;
import in.ecom.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        var categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new APIException("No category created till now.");
        }
        
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return "Category with categoryId : " + categoryId + " deleted successfully!!!";
    }

    @Override
    public Category update(Category category, Long categoryId) {
        Category updatedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        updatedCategory.setCategoryName(category.getCategoryName());

        return categoryRepository.save(updatedCategory);
    }
}
