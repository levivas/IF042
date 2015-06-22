package com.if42.tester.repository;

import com.if42.tester.entity.Category;
import com.if42.tester.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Integer>, PagingAndSortingRepository<Test, Integer> {
    public Test findByTestId(int id);
    public Test findByName(String name);
    public List<Test> findByCategory(Category category);
	
	@Query(value = "SELECT t from Test t WHERE t.category.categoryId = :categoryId ORDER BY t.name")
    public Page<Test> findPageByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

    @Query(value = "SELECT t from Test t WHERE t.category.categoryId = :categoryId")
    public List<Test> findByCategoryId(@Param("categoryId") Integer categoryId);
}
