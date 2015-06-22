package com.if42.tester.repository;

import com.if42.tester.entity.Category;
import com.if42.tester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>, PagingAndSortingRepository<Category, Integer> {

    public Category findByTitle(String title);

    @Query(value = "SELECT c FROM Category c WHERE c IN (:categories)")
    Page<Category> findAllByUserId(@Param("categories") List<Category> categories, Pageable pageable);
}
