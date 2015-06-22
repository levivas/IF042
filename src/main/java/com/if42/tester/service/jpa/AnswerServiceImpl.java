package com.if42.tester.service.jpa;

import com.if42.tester.entity.Answer;
import com.if42.tester.entity.Question;
import com.if42.tester.repository.AnswerRepository;
import com.if42.tester.service.AnswerService;
import com.if42.tester.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("answerService")
@Repository
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
    QuestionService questionService;

    @Transactional(readOnly = true)
	public List<Answer> findAllAnswer() {
		return answerRepository.findAll();
	}

    @Transactional(readOnly = true)
	public List<Answer> findByQuestion(Integer questionId) {
            return answerRepository.findAllByQuestionId(questionId);
        }

    public void addAnswer(Answer answer) {
		answerRepository.save(answer);
	}
	
	public void addAnswer(Integer questionID, Answer answer) {
		Question q = questionService.findQuestionById(questionID);
		answer.setQuestion(q);
		answerRepository.save(answer);
	}

    public Answer addAnswer(Question question, Answer answer) {
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }

	public void deleteAnswer(Integer answerId) {
		answerRepository.delete(answerId);
	}

	public void updateAnswer(Integer answerId, Answer answer) {
		Answer currentAnswer = answerRepository.findOne(answerId);
		currentAnswer.setCorrect(answer.isCorrect());
		currentAnswer.setText(answer.getText());
		answerRepository.save(currentAnswer);
	}

    @Transactional(readOnly=true)
	public Answer findAnswerById(Integer id) {
		return answerRepository.findOne(id);
	}

    @Transactional(readOnly=true)
    public Page<Answer> findAllByPage(Pageable pageable) {
        return answerRepository.findAll(pageable);
    }

    public void deleteAnswersByQuestionId(int questionId) {
        answerRepository.delete(answerRepository.findAllByQuestionId(questionId));
    }
}
