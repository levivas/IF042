package com.if42.tester.service;

import com.if42.tester.entity.GraphPoint;
import com.if42.tester.entity.SessionAnswer;

import java.util.List;

public interface SessionAnswerService {

    public List<GraphPoint> statisticsForTestResult(Integer testResultId, Integer userId);

    public void updateSessionAnswersForSessionQuestion(List<SessionAnswer> sessionAnswerList, Integer sessionQuestionId);

}
