package com.if42.tester.service.jpa;

import com.if42.tester.entity.GraphPoint;
import com.if42.tester.entity.SessionAnswer;
import com.if42.tester.repository.SessionAnswerRepository;
import com.if42.tester.service.SessionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("sessionAnswerService")
@Repository
@Transactional
public class SessionAnswerServiceImpl implements SessionAnswerService {

    @Autowired
    SessionAnswerRepository sessionAnswerRepository;

    public List<GraphPoint> statisticsForTestResult(Integer testResultId, Integer userId) {
        List<GraphPoint> points = new ArrayList<GraphPoint>();

        long easy   = sessionAnswerRepository.getCorrectUserAnswersCountForTestResultByDifficulty(testResultId, 1, userId);
        long normal = sessionAnswerRepository.getCorrectUserAnswersCountForTestResultByDifficulty(testResultId, 2, userId);
        long hard   = sessionAnswerRepository.getCorrectUserAnswersCountForTestResultByDifficulty(testResultId, 3, userId);
        long allEasy    = sessionAnswerRepository.getAllUserAnswersCountForTestResultByDifficulty(testResultId, 1, userId);
        long allNormal  = sessionAnswerRepository.getAllUserAnswersCountForTestResultByDifficulty(testResultId, 2, userId);
        long allHard    = sessionAnswerRepository.getAllUserAnswersCountForTestResultByDifficulty(testResultId, 3, userId);

        points.add(new GraphPoint(easy * 100.0 / allEasy, "Easy"));
        points.add(new GraphPoint(normal * 100.0 / allNormal, "Normal"));
        points.add(new GraphPoint(hard * 100.0 / allHard, "Hard"));

        return points;
    }

    public void updateSessionAnswersForSessionQuestion(List<SessionAnswer> sessionAnswerList, Integer sessionQuestionId){
        sessionAnswerRepository.removeBySessionQuestionId(sessionQuestionId);
        sessionAnswerRepository.save(sessionAnswerList);
    }




}
