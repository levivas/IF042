package com.if42.tester.repository;

import com.if42.tester.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>, PagingAndSortingRepository<Question, Integer> {

    @Query("SELECT q FROM Question q WHERE q.test.testId= :testId")
    public List<Question> findByTest(@Param("testId") Integer testId);

    @Query("SELECT q FROM Question q WHERE q.test.testId= :testId")
    Page<Question> findPageByTestId(@Param("testId") Integer testId, Pageable pageable);
}
