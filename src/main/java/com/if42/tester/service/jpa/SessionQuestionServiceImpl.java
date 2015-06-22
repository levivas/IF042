package com.if42.tester.service.jpa;

import com.if42.tester.entity.SessionQuestion;
import com.if42.tester.repository.SessionQuestionRepository;
import com.if42.tester.service.SessionQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sessionQuestionService")
@Repository
@Transactional
public class SessionQuestionServiceImpl implements SessionQuestionService {

    @Autowired
    private SessionQuestionRepository sessionQuestionRepository;

    @Transactional(readOnly = true)
    public List<SessionQuestion> findAllSessionQuestions() {
        return sessionQuestionRepository.findAll();
    }

    public void addSessionQuestion(SessionQuestion sessionQuestion) {
        sessionQuestionRepository.save(sessionQuestion);
    }

    public void addSessionQuestions(List<SessionQuestion> sessionQuestionList) {
        sessionQuestionRepository.save(sessionQuestionList);
    }

    public void removeSessionQuestion(SessionQuestion sessionQuestion) {
        sessionQuestionRepository.delete(sessionQuestion);
    }

    public void removeSessionQuestion(Integer id) {
        sessionQuestionRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public SessionQuestion findSessionQuestionById(Integer id) {
        return sessionQuestionRepository.findOne(id);
    }
}
