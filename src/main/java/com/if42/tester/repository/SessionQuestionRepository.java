package com.if42.tester.repository;

import com.if42.tester.entity.Question;
import com.if42.tester.entity.SessionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SessionQuestionRepository extends JpaRepository<SessionQuestion, Integer> {

    @Transactional
    public SessionQuestion findOne(Integer id);

    @Transactional
    public SessionQuestion findByQuestion(Question question);
}
