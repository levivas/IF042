package com.if42.tester.controller;

import com.if42.tester.entity.Answer;
import com.if42.tester.entity.Question;
import com.if42.tester.entity.Test;
import com.if42.tester.entity.User;
import com.if42.tester.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final int PAGE_SIZE = 8;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestService testService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * Show tests in chosen category
     * @param map
     * @param categoryId
     * @param page
     * @return testsInCategory.jsp
     */
    @RequestMapping(value = "/tests", method = RequestMethod.GET)
    public String listTestsInCategory(Map<String, Object> map,
                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId,
                                      @RequestParam(value = "page", defaultValue = "0") Integer page) {

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<Test> testPages = testService.findFromCategoryByPage(categoryId, pageRequest);

        map.put("categId", categoryId);
        map.put("testList", testPages.getContent());
        map.put("pageCount", testPages.getTotalPages());
        map.put("currentPage", page);

        return "testsInCategory";
    }

    /**
     * puts test that we want to edit into map and calls add method
     * @param testId
     * @param map
     * @param principal
     * @return
     */
    @RequestMapping(value = "/tests/edit")
    public String editTest(@RequestParam(value = "id") Integer testId,
                           @RequestParam(value = "categId", defaultValue = "-1") Integer categoryId,
                               Map<String, Object> map,
                               Principal principal) {
        Test test = testService.findTestById(testId);
        map.put("test", test);
        // like adding but form will be filled with data
        return addTest(categoryId, map,principal);
    }

    /**
     * Show page for adding new test
     * @param map
     * @param principal
     * @return testAdd.jsp
     */
    @RequestMapping(value = "/tests/add", method = RequestMethod.GET)
    public String addTest(@RequestParam(value = "categId", defaultValue = "1") Integer categoryId, Map<String, Object> map,Principal principal) {

        map.put("newTest", new Test());

        User user = userService.findUserByUsername(principal.getName());
        if(user.getGroup().getGroupId() < 3) {
            map.put("categoryList", categoryService.findAllCategory());
        } else {
            map.put("categoryList", user.getCategories());
        }
        map.put("categId", categoryId);
        return "testAdd";
    }

    /**
     * Accepts new test from page and adds it
     * @param newTest - new instanse of Test
     * @param bindingResult
     * @param categoryId
     * @param map
     * @param principal
     * @return the same page if validation fails and tests page else
     */
    @RequestMapping(value = "/tests/addtest", method = RequestMethod.POST)
    public String showAddTestPage(@ModelAttribute(value = "newTest") @Valid Test newTest,
                                  BindingResult bindingResult,
                                  @RequestParam(value = "categoryId") Integer categoryId,
                                  Map<String, Object> map, Principal principal) {

        //validation
        boolean edit = true;

        if((newTest.getTestId())!= 0){
            String testName = testService.findTestById(newTest.getTestId()).getName(); //get test name from BD
            if((testService.testExists(newTest))&&!(testName.equals(newTest.getName()))) {
                bindingResult.rejectValue("name", "error.newTest", "This name for test already exists");
            }
            edit = false;
        } else if(edit & (testService.testExists(newTest))){
            bindingResult.rejectValue("name", "error.newTest", "This name for test is already exist");
        }

        if (bindingResult.hasErrors()){
            newTest.setCategory(categoryService.findCategoryById(categoryId));
            map.put("test", newTest);
            logger.warn("New test didn't pass validation");
            User user = userService.findUserByUsername(principal.getName());
            if(user.getGroup().getGroupId() < 3) {
                map.put("categoryList", categoryService.findAllCategory());
            } else {
                map.put("categoryList", user.getCategories());
            }
            return "testAdd";
        }
        //end validation

        newTest.setCategory(categoryService.findCategoryById(categoryId));
        if(testService.findTestById(newTest.getTestId()) != null) {
            testService.updateTest(newTest);
            logger.info("Test with id {} updated", newTest.getTestId());
        } else {
            testService.addTest(newTest);
            logger.info("Test added");
        }

        return "redirect:/tests?categoryId=" + categoryId;
    }

    /**
     * Shows a page with questions of test
     * @param map
     * @param testId
     * @return
     */
    @RequestMapping(value = "/tests/questions", method = RequestMethod.GET)
    public String listQuestions(Map<String, Object> map,
                                @RequestParam(value = "testId", defaultValue = "1") Integer testId,
                                @RequestParam(value = "page", defaultValue = "0") Integer page) {

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<Question> questions = questionService.findPageByTestId(testId, pageRequest);

        map.put("pageCount", questions.getTotalPages());
        map.put("currentPage", page);

        map.put("questionList", questions.getContent());
        map.put("test", testService.findTestById(testId));
        logger.info("Questions page opened for test id " + testId);
        return "questions";
    }

    /**
     * List answers for question
     * Shows a page with answers of question
     * @param questionId
     * @param map
     * @return
     */
    @RequestMapping(value = "/tests/answers")
    public String listAnswers(@RequestParam("questionId") Integer questionId,
                              Map<String, Object> map) {
        Question question = questionService.findQuestionById(questionId);
        map.put("answerList", question.getAnswers());
        map.put("questionTitle", question.getTitle());
        logger.info("Questions page opened for question id " + questionId);

        return "answers";
    }

    /**
     * add to map question with given id and show editing page that will be filled with some data
     * @param map
     * @param questionId
     * @return
     */
    @RequestMapping(value = "/tests/questions/edit", method = RequestMethod.GET)
    public String editQuestion(Map<String, Object> map,
                               @RequestParam(value = "id", defaultValue = "-1") Integer questionId) {
        Question question = questionService.findQuestionById(questionId);
        map.put("question", question);
        map.put("testId", question.getTest().getTestId());
        map.put("type", question.getType() == 0 ? "radio" : "checkbox");
        logger.info("Starteed to edit question with id " + questionId);
        return "addQuestion";
    }

    /**
     * Show page for adding question
     * @param map
     * @param testId
     * @return
     */
    @RequestMapping(value = "/tests/questions/add", method = RequestMethod.GET)
    public String showAddQuestionPage(Map<String, Object> map,
                                      @RequestParam("testId") Integer testId) {
        map.put("testId", testId);
        map.put("type", "radio");
        logger.info("Adding question");
        return "addQuestion";
    }

    /**
     * Accepts data from addQuestion page and saves it
     * @param parameters - data sent in request
     * @return
     */
    @RequestMapping(value = "/tests/addQuestion", method = RequestMethod.POST)
    public String addQuestion(@RequestParam("correct") Integer[] correct,
                              @RequestParam Map<String, String> parameters) {
        Test test = new Test();
        int testId = Integer.parseInt(parameters.get("testId"));
        test.setTestId(testId);

        Question question;
        if(!parameters.get("questionId").equals("")) {
            question = questionService.findQuestionById(Integer.parseInt(parameters.get("questionId")));
            questionService.deleteQuestion(question.getQuestionId());
        }
        question = new Question();
        question.setRank(Integer.parseInt(parameters.get("rank")));
        question.setType(parameters.get("type").equals("0") ? 0 : 1);
        question.setTitle(parameters.get("questionText"));
        question = questionService.addQuestion(testId, question);

        int i = 1;
        Arrays.sort(correct);
        while(parameters.containsKey("answer" + i)) {
            Answer answer = new Answer(Arrays.binarySearch(correct, i) >= 0, parameters.get("answer" + i));
            answerService.addAnswer(question, answer);
            ++i;
        }
        logger.info("Question added");
        return "redirect:/tests/questions?testId=" + testId;
    }

    /**
     * Processes requests for deleting tests and questions
     * @param what - test/question
     * @param id
     */
    @RequestMapping(value = "/tests/delete", method = RequestMethod.POST) //TODO
    @ResponseStatus(value = HttpStatus.OK)
    public void  delete(@RequestParam("what") String what,
                       @RequestParam("id") Integer id,
                       Principal principal) {
        if(what == null | id == null) return;
        User user = userService.findUserByUsername(principal.getName());
        if(user.getGroup().getRole().getRoleId() > 3) return;
        if("test".equals(what)) {
            testService.deleteTest(id);
        } else if("question".equals(what)) {
            questionService.deleteQuestion(id);
        }
    }

    /** This method getting tests in JSON by category id */
    @RequestMapping(value="/tests/getTestsByCategory", params="id")
    @ResponseBody
    public List<Test> getTestsByCategoryId(@RequestParam("id") Integer id) {
        return testService.findTestByCategoryId(id);
    }
}

