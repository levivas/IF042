package com.if42.tester.service.jpa;

import com.if42.tester.entity.Question;
import com.if42.tester.entity.Test;
import com.if42.tester.repository.QuestionRepository;
import com.if42.tester.service.QuestionService;
import com.if42.tester.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("questionService")
@Repository
@Transactional
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private TestService testService;
	
	@Transactional(readOnly = true)
	public List<Question> findAllQuestion() {
		return questionRepository.findAll();
	}

    @Transactional(readOnly = true)
	public List<Question> findByTest(Integer testId) {
        return questionRepository.findByTest(testId);
	}

    public void addQuestion( Question question) {
        questionRepository.save(question);
    }

	public Question addQuestion(Integer testID, Question question) {
		Test t = new Test();
        t.setTestId(testID);
		question.setTest(t);
        testService.setModified(testService.findTestById(testID));
		return questionRepository.save(question);
	}

	public void updateQuestion(Integer questionId, Question question) {
		Question currentQuestion= questionRepository.findOne(questionId);
		currentQuestion.setRank(question.getRank());
		currentQuestion.setTitle(question.getTitle());
		questionRepository.save(currentQuestion);
	}
	
	public void deleteQuestion(Integer questionId) {
        questionRepository.delete(questionId);
	}

	@Transactional(readOnly = true)
	public Question findQuestionById(Integer id) {
		return questionRepository.findOne(id);
	}

    @Transactional(readOnly=true)
    public Page<Question> findAllByPage(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public Page<Question> findPageByTestId(Integer testId, Pageable pageable) {
        return questionRepository.findPageByTestId(testId, pageable);
    }
}
