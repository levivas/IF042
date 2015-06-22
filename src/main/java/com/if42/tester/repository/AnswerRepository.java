package com.if42.tester.repository;

import com.if42.tester.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface AnswerRepository
        extends JpaRepository<Answer, Integer>
        , PagingAndSortingRepository<Answer, Integer> {

    @Query("SELECT a FROM Answer a WHERE a.question.questionId= :q")
    public List<Answer> findAllByQuestionId(@Param("q") Integer q);


}
