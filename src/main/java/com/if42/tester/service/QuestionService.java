package com.if42.tester.service;

import com.if42.tester.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {

	public List<Question> findAllQuestion();
	
	public List<Question> findByTest(Integer testId);

    public void addQuestion(Question question);

	public Question addQuestion(Integer testID, Question question);

	public void updateQuestion(Integer questionId, Question question);
	
	public void deleteQuestion(Integer questionId);

	public Question findQuestionById(Integer id);

    public Page<Question> findAllByPage(Pageable pageable);

    Page<Question> findPageByTestId(Integer testId, Pageable pageable);
}
