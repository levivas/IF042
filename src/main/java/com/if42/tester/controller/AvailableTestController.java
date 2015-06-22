package com.if42.tester.controller;

import com.if42.tester.entity.*;
import com.if42.tester.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This controller using for CRUD operation with
 * available tests
 *
 * @author Kocjuba Gregory
 */
@Controller
@RequestMapping("/availableTest")
public class AvailableTestController {

    @Autowired
    private AvailableTestService availableTestService;

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CategoryService categoryService;

    private static final int PAGE_SIZE = 10;

    /** This method use for showing assigned tests */
    @RequestMapping(value = "availableTest", method = RequestMethod.GET)
    public String listAvailableTests(Map<String, Object> map,
                                     @RequestParam(value = "page", defaultValue = "0") Integer page) {

        Date currentDate = new Date();
        Timestamp currentTime = new Timestamp(currentDate.getTime());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> list = (List<GrantedAuthority>) auth.getAuthorities();
        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<AvailableTest> availableTests;

        if(list.contains(new SimpleGrantedAuthority("ROLE_TEACHER"))){
            availableTests = availableTestService.findAllByPage(pageRequest);
        }else{
            User user = userService.findUserByUsername(auth.getName());
            availableTests = availableTestService.findByUserByPage(pageRequest, user);
        }
        map.put("currentTime", currentTime);
        map.put("availableTestList", availableTests.getContent());
        map.put("pageCount", availableTests.getTotalPages());
        map.put("currentPage", page);


        return "availableTest";
    }

    /** This method getting form for assigning new test on user*/
    @RequestMapping(value = "/assign", method = RequestMethod.GET)
    public String assignTest(Map<String, Object> map) {

        map.put("availableTest", new AvailableTest());
        map.put("categoryList", categoryService.findAllCategory());
        map.put("groupList", groupService.findAllStudentGroup());
        map.put("action", "availableTest/add");

        return "updateAvailableTest";
    }

    /** This method add assigned test in database */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String assignTest(@ModelAttribute("availableTest") AvailableTest availableTest,
                          @RequestParam Integer testSelect,
                          @RequestParam Integer userSelect,
                          @RequestParam Integer groupSelect) {

        Test test = new Test();
        test.setTestId(testSelect);
        if (userSelect.equals(-2)){
            Group group = new Group();
            group.setGroupId(groupSelect);
            List<User> users = userService.findUserByGroup(group);
            for (User user : users) {
                availableTest.setTest(test);
                availableTest.setUser(user);
                availableTestService.addAvailableTest(availableTest);
            }
        } else {
            User user = new User();
            user.setUserId(userSelect);
            availableTest.setTest(test);
            availableTest.setUser(user);
            availableTestService.addAvailableTest(availableTest);
        }


        return "redirect:/availableTest";
    }

    /** This method delete available test from database */
    @RequestMapping("/delete/")
    public String deleteAvailableTest(@RequestParam(value = "availableTestId") Integer availableTestId) {
        availableTestService.removeAvailableTestById(availableTestId);
        return "redirect:/availableTest";
    }

    /** This method getting form for editing assigning test on user */
    @RequestMapping(value = "/edit/", method = RequestMethod.GET)
    public String showEditUser(Map<String, Object> map,
                               @RequestParam("availableTestId") Integer availableTestId) {

        AvailableTest availableTest = availableTestService.findAvailableTestById(availableTestId);

        map.put("currentTest",testService.findTestById(availableTest.getTest().getTestId()));
        map.put("currentUser",userService.findUserById(availableTest.getUser().getUserId()));
        map.put("currentGroup",groupService.findGroupById(availableTest.getUser().getGroup().getGroupId()));
        map.put("currentCategory",categoryService.findCategoryById(availableTest.getTest().getCategory().getCategoryId()));

        map.put("testList", testService.findTestByCategoryId(availableTest.getTest().getCategory().getCategoryId()));
        map.put("userList", userService.findUserByGroup(availableTest.getUser().getGroup()));
        map.put("groupList", groupService.findAllStudentGroup());
        map.put("categoryList", categoryService.findAllCategory());

        map.put("availableTest", availableTest);
        map.put("availableTestList", availableTestService.findAllAvailableTest());
        map.put("action", "/availableTest/addEdit/?availableTestId=" + availableTest.getAvailableTestId());

        return "updateAvailableTest";
    }

    /** This method update assigned test */
    @RequestMapping(value = "/addEdit/", method = RequestMethod.POST)
    public String editAvailableTest(@ModelAttribute("availableTest") AvailableTest availableTest,
                           @RequestParam(value = "availableTestId") Integer availableTestId,
                           @RequestParam Integer testSelect,
                           @RequestParam Integer userSelect,
                           @RequestParam Integer groupSelect) {

        Test test = testService.findTestById(testSelect);
        if (userSelect.equals(-2)){
            availableTestService.removeAvailableTestById(availableTestId);
            Group group = new Group();
            group.setGroupId(groupSelect);
            List<User> users = userService.findUserByGroup(group);
            for (User user : users) {
                availableTest.setTest(test);
                availableTest.setUser(user);
                availableTestService.addAvailableTest(availableTest);
            }
        } else {
            User user = userService.findUserById(userSelect);
            availableTest.setTest(test);
            availableTest.setUser(user);
            availableTest.setAvailableTestId(availableTestId);
            availableTestService.addAvailableTest(availableTest);
        }

        return "redirect:/availableTest";
    }
}
