package com.example.demo_DGB.Service;
import com.example.demo_DGB.model.category;
import com.example.demo_DGB.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository CategoryRepository;
    public List<category> listCategories() {
        return CategoryRepository.findAll();
    }
    public category searchCategory(Long id) {
        return CategoryRepository.findById(id).orElse(null);
    }
    public category saveCategory(category c) {
        return CategoryRepository.save(c);
    }
    public boolean deleteCategory(Long id) {
        if (CategoryRepository.existsById(id)) {
            CategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }


}