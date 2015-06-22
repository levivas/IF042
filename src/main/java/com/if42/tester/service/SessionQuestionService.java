package com.if42.tester.service;

import com.if42.tester.entity.SessionQuestion;

import java.util.List;

public interface SessionQuestionService {

    public List<SessionQuestion> findAllSessionQuestions();

    public void addSessionQuestion(SessionQuestion sessionQuestion);

    public void addSessionQuestions(List<SessionQuestion> sessionQuestionList);

    public void removeSessionQuestion(SessionQuestion sessionQuestion);

    public void removeSessionQuestion(Integer id);

    public SessionQuestion findSessionQuestionById(Integer id);
}
