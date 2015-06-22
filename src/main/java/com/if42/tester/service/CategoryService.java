package com.if42.tester.service;

import com.if42.tester.entity.Category;
import com.if42.tester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryService {

	public List<Category> findAllCategory();

	public void addCategory(Category category);

	public void removeCategory(Integer categoryId);

	public Category findCategoryById(Integer id);

    public Category findByTitle(String title);

    public boolean isExist(Category category);

    public Page<Category> findAllByPage(Pageable pageable);

    public  List<Category> getUsersCategories(int[] categoryIds);

    Page<Category> findByUserByPage(User user, Pageable pageable);
}
