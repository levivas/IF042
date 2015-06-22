package com.if42.tester.controller;

import com.if42.tester.entity.*;
import com.if42.tester.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;

/**
 * This controller is used for user testing
 *
 * @author Kocjuba Gregory
 */
@Controller
@RequestMapping("testing")
public class TestingController {

    public static final int EASY = 1;
    public static final int NORMAL = 2;
    public static final int HARD = 3;
    final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AvailableTestService availableTestService;

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestsResultService testsResultService;

    @Autowired
    private SessionTestService sessionTestService;

    @Autowired
    private SessionQuestionService sessionQuestionService;

    @Autowired
    private SessionAnswerService sessionAnswerService;

    @ResponseBody
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public void saveAnswer(@RequestParam(value = "sessionQuestionId") Integer sessionQuestionId,
                           @RequestParam(value = "answerIds", defaultValue = "-1") Integer[] answerIds) {

        logger.info("Saving " + answerIds.length + " answers for question " + sessionQuestionId);

        SessionQuestion sessionQuestion = new SessionQuestion();
        sessionQuestion.setSessionQuestionId(sessionQuestionId);
        List<SessionAnswer> sessionAnswerList = new ArrayList<SessionAnswer>();

        if ( answerIds[0] != -1){

            for (Integer answerId : answerIds) {
                System.out.println(answerId);
                Answer answer = new Answer();
                answer.setAnswerId(answerId);
                SessionAnswer sessionAnswer = new SessionAnswer();
                sessionAnswer.setSessionQuestion(sessionQuestion);
                sessionAnswer.setAnswer(answer);
                sessionAnswerList.add(sessionAnswer);
                logger.info("session Q id "+sessionQuestionId);
                logger.info("answers for Q "+answerId);
            }
            sessionAnswerService.updateSessionAnswersForSessionQuestion(sessionAnswerList, sessionQuestionId);
        }
        logger.info("Answers for question " + sessionQuestionId + " is saved");
    }


    @RequestMapping(value = "getQuestion", method = RequestMethod.GET)
    public String getQuestionForTest(
            @RequestParam(value = "SessionQuestionId") Integer sessionQuestionId
            , Map<String, Object> map){
        SessionQuestion sessionQuestion = sessionQuestionService.findSessionQuestionById(sessionQuestionId);
        String type = sessionQuestion.getQuestion().getType() == 0 ? "radio" : "checkbox";
        map.put("sessionQuestion", sessionQuestion);
        map.put("type", type);
        map.put("first", false);

        return "questionForTest";
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String startTest(Map<String, Object> map,
                            @RequestParam(value = "availableTestId", defaultValue = "-1") Integer availableTestId,
                            @RequestParam(value = "testId", defaultValue = "-1") Integer testId) {

        //Getting information about current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorityList = (List<GrantedAuthority>) auth.getAuthorities();

        //getting current time
        Timestamp currentTime = new Timestamp(new Date().getTime());

        //if current user - teacher
        if(authorityList.contains(new SimpleGrantedAuthority("ROLE_TEACHER"))){

            //find question for test
            Test test = testService.findTestById(testId);
            List<Question> allQuestions = questionService.findByTest(testId);

            //getting random questions by configuration
            Collections.shuffle(allQuestions);
            List<Question> questionsForTesting = new ArrayList<Question>();

            //TODO REFACTOR FOR EACH

            int easyQuestionsCount = test.getEasyQuestionsCount();
            int normalQuestionsCount = test.getNormalQuestionsCount();
            int hardQuestionsCount = test.getHardQuestionsCount();

            for (Question question : allQuestions) {

                switch (question.getRank()){
                    case EASY:
                        if(easyQuestionsCount!=0) {
                            questionsForTesting.add(question);
                            --easyQuestionsCount;
                        }
                        break;
                    case NORMAL:
                        if(normalQuestionsCount!=0) {
                            questionsForTesting.add(question);
                            --normalQuestionsCount;
                        }
                        break;
                    case HARD:
                        if(hardQuestionsCount!=0) {
                            questionsForTesting.add(question);
                            --hardQuestionsCount;
                        }
                        break;
                }
            }

            //creating session test with session questions
            SessionTest sessionTest = new SessionTest();
            List<SessionQuestion> sessionQuestionList = new ArrayList<SessionQuestion>();

            for (Question question : questionsForTesting) {
                SessionQuestion sessionQuestion = new SessionQuestion();
                sessionQuestion.setQuestion(question);
                sessionQuestion.setSessionTest(sessionTest);
                sessionQuestionList.add(sessionQuestion);
            }

            sessionTest.setStartTime(currentTime);
            sessionTest.setSessionQuestions(sessionQuestionList);
            sessionTest.setTest(test);

            //saving session test with session questions
            sessionTestService.addSessionTest(sessionTest);
            sessionQuestionService.addSessionQuestions(sessionQuestionList);

            //sending to user testing page with first question
            SessionQuestion sessionQuestion = sessionQuestionList.get(0);
            int[] sessionQuestionIds = new int[sessionQuestionList.size()];

            for (int i=0; i<sessionQuestionList.size(); i++) {
                sessionQuestionIds[i] = sessionQuestionList.get(i).getSessionQuestionId();
            }

            String type = sessionQuestion.getQuestion().getType() == 0 ? "radio" : "checkbox";

            map.put("sessionQuestionIds", sessionQuestionIds);
            map.put("sessionQuestion", sessionQuestion);
            map.put("duration", test.getDuration());
            map.put("type", type);
            map.put("first", true);

            return "testing";

        } else  {

            AvailableTest availableTest=availableTestService.findAvailableTestById(availableTestId);
            List<SessionQuestion> sessionQuestionList;
            int duration;

            //checks if user start new test or continue test
            if (availableTest.getSessionTest() == null){

                Test test=availableTest.getTest();
                List<Question> allQuestions = questionService.findByTest(test.getTestId());

                //getting random questions by configuration
                Collections.shuffle(allQuestions);
                List<Question> questionsForTesting = new ArrayList<Question>();

                int easyQuestionsCount = test.getEasyQuestionsCount();
                int normalQuestionsCount = test.getNormalQuestionsCount();
                int hardQuestionsCount = test.getHardQuestionsCount();

                for (Question question : allQuestions) {

                    switch (question.getRank()){
                        case EASY:
                            if(easyQuestionsCount!=0) {
                                questionsForTesting.add(question);
                                --easyQuestionsCount;
                            }
                            break;
                        case NORMAL:
                            if(normalQuestionsCount!=0) {
                                questionsForTesting.add(question);
                                --normalQuestionsCount;
                            }
                            break;
                        case HARD:
                            if(hardQuestionsCount!=0) {
                                questionsForTesting.add(question);
                                --hardQuestionsCount;
                            }
                            break;
                    }
                }

                if( currentTime.getTime() > availableTest.getStartTime().getTime()
                        && currentTime.getTime() < availableTest.getFinishTime().getTime() ) {

                    //creating session test with session questions
                    SessionTest sessionTest = new SessionTest();
                    sessionQuestionList = new ArrayList<SessionQuestion>();

                    for (Question question : questionsForTesting) {
                        SessionQuestion sessionQuestion = new SessionQuestion();
                        sessionQuestion.setQuestion(question);
                        sessionQuestion.setSessionTest(sessionTest);
                        sessionQuestionList.add(sessionQuestion);
                    }

                    sessionTest.setStartTime(currentTime);

                    long finishTime = test.getDuration() * 1000 * 60 + currentTime.getTime();
                    sessionTest.setFinishTime(new Timestamp(finishTime));

                    sessionTest.setSessionQuestions(sessionQuestionList);
                    sessionTest.setTest(test);

                    //saving session test with session questions and updating availableTest
                    sessionTestService.addSessionTest(sessionTest);
                    sessionQuestionService.addSessionQuestions(sessionQuestionList);
                    availableTest.setSessionTest(sessionTest);
                    availableTestService.addAvailableTest(availableTest);
                    duration = availableTest.getTest().getDuration();

                    } else {
                        return "redirect:denied";
                    }

                } else {
                    sessionQuestionList = availableTest.getSessionTest().getSessionQuestions();
                    duration = (int)(availableTest.getSessionTest().getFinishTime().getTime() - currentTime.getTime())/1000/60;
                }

                //sending to user testing page with first question
                SessionQuestion sessionQuestion = sessionQuestionList.get(0);
                int[] sessionQuestionIds = new int[sessionQuestionList.size()];

                for (int i=0; i<sessionQuestionList.size(); i++) {
                    sessionQuestionIds[i] = sessionQuestionList.get(i).getSessionQuestionId();
                }

                String type = sessionQuestion.getQuestion().getType() == 0 ? "radio" : "checkbox";

                map.put("duration", duration);
                map.put("sessionQuestionIds", sessionQuestionIds);
                map.put("sessionQuestion", sessionQuestion);
                map.put("type", type);
                map.put("first", true);

                return "testing";



        }
    }

    /** This method is used get user test result */
    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    public String getResult(Map<String, Object> map,
                            @RequestParam(value = "sessionTestId") Integer sessionTestId) {

        SessionTest sessionTest = sessionTestService.findSessionTestById(sessionTestId);
        Test test = sessionTest.getTest();
        AvailableTest availableTest = null;
        User user = null;

        //if current user is student
        if (sessionTest.getAvailableTest() != null){

            //get information about current user
            availableTest = sessionTest.getAvailableTest();
            user = availableTest.getUser();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            //protection for illegal access
            if (!user.getUserName().equals(auth.getName())){

                return "redirect:denied";
            }
        }

        List<Question> questions = questionService.findByTest(test.getTestId());

        float maxAnswersGrade = test.getEasyQuestionsCount()*EASY
                + (test.getNormalQuestionsCount() * NORMAL)
                + (test.getHardQuestionsCount() * HARD);

        float testMarkPercents = 0;

        float resultAnswersGrade = 0;

        for (SessionQuestion sessionQuestion : sessionTest.getSessionQuestions()) {
            Question question = sessionQuestion.getQuestion();
            if(question.getType() == 0){
                //getting first answer in answers because it's question with one answer
                List<SessionAnswer> sessionAnswerList = sessionQuestion.getSessionAnswers();
                if (sessionAnswerList.size() != 0){

                    Answer answer = sessionAnswerList.get(0).getAnswer();
                    if ( (answer != null) && answer.isCorrect()){
                        resultAnswersGrade+=question.getRank();
                    }

                }

            } else {
                float rightAnswersCount=0;
                float wrongAnswersCount=0;
                float userRigthAnswersCount=0;
                float userWrongAnswersCount=0;

                List<SessionAnswer> sessionAnswers = sessionQuestion.getSessionAnswers();

                List<Answer> answers = sessionQuestion.getQuestion().getAnswers();
                List<Answer> userAnswers = new ArrayList<Answer>();

                for (SessionAnswer sessionAnswer : sessionAnswers) {
                    userAnswers.add(sessionAnswer.getAnswer());
                }

                for (Answer answer : answers) {
                    if(answer.isCorrect()){
                        ++rightAnswersCount;
                        if(userAnswers.contains(answer)){
                            ++userRigthAnswersCount;
                        }
                    } else {
                        ++wrongAnswersCount;
                        if(userAnswers.contains(answer)){
                            ++userWrongAnswersCount;
                        }
                    }
                }

                float questionResult = ((userRigthAnswersCount/rightAnswersCount)-(userWrongAnswersCount/wrongAnswersCount))*question.getRank();

                if (questionResult>0){
                    resultAnswersGrade += questionResult;
                }

            }
        }


        testMarkPercents = resultAnswersGrade * 100 / maxAnswersGrade;
        TestsResult testResult = new TestsResult();
        testResult.setMarkPercents(Math.round(testMarkPercents));
        testResult.setTest(test);

        //if current user is student saving info about testing
        if (sessionTest.getAvailableTest() != null){

                testResult.setTest(test);
                testResult.setSessionTest(sessionTest);
                testResult.setUser(user);
                testsResultService.addTestsResult(testResult);

                //removing test from available tests
                availableTestService.removeAvailableTest(availableTest);
        } //TODO drop session test from sessionTests in DB if current user Teacher - else

        map.put("result", testResult);

        return "userTestResult";
    }

}
