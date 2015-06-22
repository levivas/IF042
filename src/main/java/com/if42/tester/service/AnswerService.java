package com.if42.tester.service;

import com.if42.tester.entity.Answer;
import com.if42.tester.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;



public interface AnswerService {


	public List<Answer> findAllAnswer();
	
	public List<Answer> findByQuestion(Integer questionId);

    public void addAnswer(Answer answer);
	
	public void addAnswer(Integer questionID, Answer answer);

    public Answer addAnswer(Question question, Answer answer);

	public void deleteAnswer(Integer answerId);

	public void updateAnswer(Integer answerId, Answer answer);
	
	public Answer findAnswerById(Integer id);

    public Page<Answer> findAllByPage(Pageable pageable);

    public void deleteAnswersByQuestionId(int questionId);
}
