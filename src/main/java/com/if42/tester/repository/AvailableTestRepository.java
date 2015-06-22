package com.if42.tester.repository;

import com.if42.tester.entity.AvailableTest;
import com.if42.tester.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AvailableTestRepository extends JpaRepository<AvailableTest, Integer>, PagingAndSortingRepository<AvailableTest, Integer> {
    public Page<AvailableTest> findByUser(Pageable pageable, User user);
}
