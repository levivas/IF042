package com.if42.tester.controller;

import com.if42.tester.entity.SessionQuestion;
import com.if42.tester.entity.TestsResult;
import com.if42.tester.entity.User;
import com.if42.tester.service.*;
import com.if42.tester.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TestsResultsController {

    private static final String ROLE_TEACHER = "ROLE_TEACHER";

    @Autowired
    private TestsResultService testsResultService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TestService testService;

    private static final int PAGE_SIZE = 10;

    /**
     * Show page with various filters for test results
     * @param map
     * @param testId
     * @param groupId
     * @param categoryId
     * @param page
     * @param dateA
     * @param dateB
     * @return
     */
    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public String printResults(Map<String, Object> map,
                               @RequestParam(value = "test", defaultValue = "-1") Integer testId,
                               @RequestParam(value = "group", defaultValue = "-1") Integer groupId,
                               @RequestParam(value = "category", defaultValue = "-1") Integer categoryId,
                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                               @RequestParam(value = "dateA", defaultValue = "0") Long dateA,
                               @RequestParam(value = "dateB", defaultValue = "0") Long dateB) {

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) auth.getAuthorities();
        Page<TestsResult> resultsPage;
        if(list.contains(new SimpleGrantedAuthority(ROLE_TEACHER))){
            resultsPage = testsResultService.findByTestGroupCategory(testId, groupId, categoryId,
                    dateA == 0 ? new Timestamp(0L) : new Timestamp(dateA),
                    dateB == 0 ? new Timestamp(new Date().getTime()) : new Timestamp(dateB),
                    pageRequest);

        } else {
            User user = userService.findUserByUsername(auth.getName());
            resultsPage = testsResultService.findByTestCategoryUserId(testId, categoryId,
                    // if start date not set it will be 1970-01-01...
                    dateA == 0 ? new Timestamp(0L) : new Timestamp(dateA),
                    // if end date not set it will be current date
                    dateB == 0 ? new Timestamp(new Date().getTime()) : new Timestamp(dateB),
                    user.getUserId(), pageRequest);
        }

        map.put("resultList", resultsPage.getContent());
        map.put("currentPage", page);
        map.put("pageCount", resultsPage.getTotalPages());

        map.put("testList", testService.findAllTest());
        map.put("groupList", groupService.findAllGroup());
        map.put("categoryList", categoryService.findAllCategory());

        return "results";
    }



    /**
     * Remove test result
     * @param id
     * @return
     */
    @RequestMapping(value = "/results/remove", method = RequestMethod.POST)
    public String deleteResult(@RequestParam(value = "id", defaultValue = "-1") Integer id) {
        if (id != -1) {
            if (testsResultService.findTestsResultById(id) != null) {
                testsResultService.removeTestsResult(id);
            }
        }
        return "results";
    }

    @RequestMapping(value = "/results/results.pdf")
    @ResponseBody
    public ResponseEntity<byte[]> generateReport(@RequestParam(value = "test", defaultValue = "-1") Integer testId,
                                                 @RequestParam(value = "group", defaultValue = "-1") Integer groupId,
                                                 @RequestParam(value = "category", defaultValue = "-1") Integer categoryId,
                                                 @RequestParam(value = "dateA", defaultValue = "0") Long dateA,
                                                 @RequestParam(value = "dateB", defaultValue = "0") Long dateB,
                                                 @RequestParam(value = "categoryName", defaultValue = "") String subject){

        List <TestsResult> testsResults = testsResultService.findByTestGroupCategoryList(testId,groupId,categoryId,
                dateA == 0 ? new Timestamp(0L) : new Timestamp(dateA),
                dateB == 0 ? new Timestamp(new Date().getTime()) : new Timestamp(dateB));

        String group = groupService.findGroupById(groupId).getName();
        ByteArrayOutputStream outputStream = PDFGenerator.generateToOutputStream(subject, group, testsResults);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/results/reviewAnswer", method = RequestMethod.GET)
    public String reviewAnswerOfPassedTest(Map<String, Object> map,
                                           @RequestParam(value = "testResultId",defaultValue = "-1") Integer testResultId){

        TestsResult testsResult = testsResultService.findTestsResultById(5);
        List<SessionQuestion> sessionQuestions = testsResult.getSessionTest().getSessionQuestions();

        map.put("testResult", testsResult);
        map.put("sessionQuestions",sessionQuestions);

        return "reviewTestResult";
    }

}
