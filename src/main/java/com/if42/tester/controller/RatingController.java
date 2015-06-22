package com.if42.tester.controller;

import com.if42.tester.entity.*;
import com.if42.tester.service.CategoryService;
import com.if42.tester.service.GroupService;
import com.if42.tester.service.TestsResultService;
import com.if42.tester.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This Controller show rating of users by groups and categories
 *
 * @author Andriy Ivanets
 */
@Controller
@RequestMapping("/rating")
public class RatingController {
    @Autowired
    private TestsResultService testsResultService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private final int PAGE_SIZE = 10;

    /**
     * Method gets list of users and their average grade and
     * sorts them by average grade
     * @param map - Map that will be mapped to the ratingUser tile
     * @param groupId  - id of group.This variable is used for sorting
     *                 rating's results by group
     * @param categoryId - id of category.This variable is used for sorting
     *                   rating's results by category
     * @return
     */
    @RequestMapping(value = "rating", method = RequestMethod.GET)
    public String printResultsForUsers(Map<String, Object> map,
                                       @RequestParam(value = "group", defaultValue = "-1") Integer groupId,
                                       @RequestParam(value = "category", defaultValue = "-1") Integer categoryId,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page) {

        //getting current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        map.put("currentUserId", userService.findUserByUsername(auth.getName()).getUserId());

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<Object []> testsResultsPages = testsResultService.findByGroupForRating(groupId, categoryId, pageRequest);

        map.put("resultList",listOfUserRating(testsResultsPages) );
        map.put("currentPage", page);
        map.put("pageCount", testsResultsPages.getTotalPages());
        map.put("groupList", groupService.findAllStudentsGroups());
        map.put("categoryList", categoryService.findAllCategory());
        map.put("position",page* PAGE_SIZE +1);

        return "ratingUsers";
    }

    /**
     * Method gets list of categories and their average grade and
     * sorts them by average grade
     * @param map - Map that will be mapped to the tableUser tile
     * @param groupId  - id of group.This variable is used for sorting
     *                 rating's results by group
     * @return
     */
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String printResultsForCategories(Map<String, Object> map,
                                            @RequestParam(value = "group", defaultValue = "-1") Integer groupId,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page){

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<Object []> testsResultsPages = testsResultService.findCategoriesAverageForRating(groupId, pageRequest);

        map.put("currentPage", page);
        map.put("pageCount", testsResultsPages.getTotalPages());
        map.put("groupList", groupService.findAllStudentsGroups());
        map.put("resultList",listOfCategoryRating(testsResultsPages));

        return "ratingCategory";

    }

    /**
     * This method creating List<UserRating>
     * @param testsResultsPages - List of arrays with two object - User and
     *                            Double(average grade)
     * @return   List<UserRating>, which will set in map.
     */
    public List<UserRating> listOfUserRating(Page<Object[]> testsResultsPages){
        List<Object[]> list = testsResultsPages.getContent();
        List<UserRating> listOfRating = new ArrayList<UserRating>();
        for (Object[] objects : list) {
            listOfRating.add(new UserRating((User)objects[0],(Double)objects[1]));
        }
        return listOfRating;
    }

    /**
     * Method sends names of categories and their average grades
     * in Json
     * @param groupId  - id of group.This variable is used for sorting
     *                 rating's results by group
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/getCategoriesJSON")
    public List<GraphPoint> sendCategoriesRating(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "group", defaultValue = "-1") Integer groupId){

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<Object []> testsResultsPages = testsResultService.findCategoriesAverageForRating(groupId, pageRequest);

        return listOfCategoryRating(testsResultsPages);
    }

    /**
     * This method creating List<GraphPoint>
     * @param testsResultsPages - List of arrays with two object - Category and
     *                            Double(average grade)
     * @return   List<GraphPoint>, which will set in map.
     */
    public List<GraphPoint> listOfCategoryRating(Page<Object[]> testsResultsPages){
        List<Object[]> list = testsResultsPages.getContent();
        List<GraphPoint> listOfRating = new ArrayList<GraphPoint>();
        for (Object[] objects:list){
            listOfRating.add(new GraphPoint((Double)objects[1],((Category)objects[0]).getTitle()));
        }
        return listOfRating;
    }


}

