package com.if42.tester.service.jpa;

import com.if42.tester.entity.Category;
import com.if42.tester.entity.User;
import com.if42.tester.repository.CategoryRepository;
import com.if42.tester.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
@Repository
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

    @Transactional(readOnly=true)
	public List<Category> findAllCategory() {
        return categoryRepository.findAll();
	}

    public void addCategory(Category category) {
		categoryRepository.save(category);
	}

	public void removeCategory(Integer categoryId) {
		categoryRepository.delete(categoryId);
	}

    @Transactional(readOnly=true)
	public Category findCategoryById(Integer id) {
		return categoryRepository.findOne(id);
	}

    @Transactional(readOnly=true)
    public Category findByTitle(String title){
        return  categoryRepository.findByTitle(title);
    }

    @Transactional(readOnly=true)
    public boolean isExist(Category category){
        return categoryRepository.findByTitle(category.getTitle()) != null;
   }

    @Transactional(readOnly=true)
    public Page<Category> findAllByPage(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findByUserByPage(User user, Pageable pageable) {
        return categoryRepository.findAllByUserId(user.getCategories(), pageable);
    }

    /**
     * This method creates list of categories. It takes an array
     * of category's id. Then we can set user this categories.
     */
    public  List<Category> getUsersCategories(int[] categoryIds) {
        List<Category> categories = new ArrayList<Category>();
        if (categoryIds != null) {
            for (Integer id : categoryIds) {
                Category category = new Category(id);
                categories.add(category);
            }

        }
        return categories;
    }
}
