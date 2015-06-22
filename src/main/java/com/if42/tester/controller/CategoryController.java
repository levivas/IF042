package com.if42.tester.controller;

import com.if42.tester.entity.Category;
import com.if42.tester.entity.User;
import com.if42.tester.service.CategoryService;
import com.if42.tester.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/category")
public class CategoryController {

    final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    private static final int PAGE_SIZE = 5;

    @RequestMapping(value = "category", method = RequestMethod.GET)
    public String listCategory(Map<String, Object> map, Principal principal,
                               @RequestParam(value = "page", defaultValue = "0") Integer page){

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<Category> categoryPages;

        User user = userService.findUserByUsername(principal.getName());
        if(user.getGroup().getRole().getRoleId() < 3) {
           categoryPages = categoryService.findAllByPage(pageRequest);
        } else {
            categoryPages = categoryService.findByUserByPage(user, pageRequest);
        }

        map.put("categoryList", categoryPages.getContent());
        map.put("currentPage", page);
        map.put("pageCount", categoryPages.getTotalPages());

        logger.info("Getting categories for table");

        return "tableCategory";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String formAddCategory(Map<String, Object> map) {

        map.put("userList", userService.findTeachersByGroup());
        map.put("category", new Category());
        map.put("categoryList",categoryService.findAllCategory());
        map.put("action", "/category/addCategory");

        return "creatingCategory";
    }

    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(@ModelAttribute("category")@Valid Category category,
                              BindingResult result,
                              Map<String, Object> map,
                              @RequestParam(value="teacherSelectIds", required = false) int[] teacherSelectIds) {

        if(teacherSelectIds != null) {
            List<User> users = new ArrayList<User>();

            for (Integer teacherId : teacherSelectIds) {
                User user = userService.findUserById(teacherId);
                users.add(user);
            }
            category.setUsers(users);
        }

        if (categoryService.isExist(category)) {
            result.rejectValue("title", "error.category", "This name for category is already exist");
        }

        if (result.hasErrors()){
            map.put("category", category);
            map.put("categoryList", categoryService.findAllCategory());
            map.put("userList", userService.findTeachersByGroup());
            map.put("currentTeacherList", category.getUsers());
            map.put("action", "/category/addCategory");
            return "creatingCategory";
        }
        categoryService.addCategory(category);

        logger.info("create new Group with Name: "+ category.getTitle());

        return "redirect:/category";
    }

    @RequestMapping("/delete")
    public String deleteCategory(@RequestParam(value = "categoryId") Integer categoryId) {

        categoryService.removeCategory(categoryId);

        logger.info("delete Category with id" + categoryId);

        return "redirect:/category";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEditCategory(Map<String, Object> map,
                                   @RequestParam(value = "categoryId") Integer categoryId) {



        Category category = categoryService.findCategoryById(categoryId);

        map.put("category", category);
        map.put("categoryList", categoryService.findAllCategory());
        map.put("userList", userService.findTeachersByGroup());
        map.put("currentTeacherList", category.getUsers());
        map.put("action", "/category/addEdit/?categoryId=" + category.getCategoryId());

        return "creatingCategory";

    }

    @RequestMapping(value = "/addEdit/", method = RequestMethod.POST)
    public String editCategory(@ModelAttribute("category")@Valid Category category,BindingResult result,
                               Map<String, Object> map,
                               @RequestParam(value="teacherSelectIds", required = false) int[] teacherSelectIds,
                               @RequestParam(value = "categoryId") Integer categoryId) {

        List<User> users = new ArrayList<User>();
        if(teacherSelectIds != null) {
            for (Integer teacherId : teacherSelectIds) {
                User user = userService.findUserById(teacherId);
                users.add(user);
            }
            category.setCategoryId(categoryId);
            category.setUsers(users);
        }

        String categoryName = categoryService.findCategoryById(categoryId).getTitle();
        if (categoryService.isExist(category) && !(categoryName.equals(category.getTitle()))) {
            result.rejectValue("title", "error.category", "This name for category is already exist");
        }

        if (result.hasErrors()){
            map.put("category", category);
            map.put("categoryList", categoryService.findAllCategory());
            map.put("userList", userService.findTeachersByGroup());
            map.put("currentTeacherList", category.getUsers());
            map.put("action", "/category/addCategory");
            return "creatingCategory";
        }


        categoryService.addCategory(category);

        logger.info("edit Category with Name: " + category.getTitle());

        return "redirect:/category";
    }

}
