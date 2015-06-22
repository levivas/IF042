package com.if42.tester.controller;

import com.if42.tester.entity.Group;
import com.if42.tester.entity.User;
import com.if42.tester.service.CategoryService;
import com.if42.tester.service.GroupService;
import com.if42.tester.service.MailService;
import com.if42.tester.service.UserService;
import com.if42.tester.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller that provides review, adding, editing, deleting users.
 * Here implements linking teachers with categories
 *
 * @author Andriy Ivanets
 */
@Controller
@RequestMapping("/user")
public class UserController {

    final Logger logger = LoggerFactory.getLogger(UserController.class);
    final int SUPER_ADMIN_ID = 1;
    final int PAGE_SIZE = 10;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Method gets  List with users that exist in the system and
     * adds it to table.But it show all users only for "SuperAdmin",
     * "Admin" will see only teachers, and students. "Teacher" will
     * see only students
     *
     * @param map     - Map that will be mapped to the tableUser tile
     * @param groupId - id of group which selects user
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String listUsers(Map<String, Object> map,
                            @RequestParam(value = "page", defaultValue = "0") Integer page,
                            @RequestParam(value = "groupId") Integer groupId) {

        PageRequest pageRequest = new PageRequest(page, PAGE_SIZE);
        Page<User> userPages = userService.findUsersByGroup(groupId, pageRequest);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final int currentUserId = userService.findUserByUsername(auth.getName()).getUserId();

        map.put("groupId", groupId);
        map.put("userList", userPages.getContent());
        map.put("currentPage", page);
        map.put("pageCount", userPages.getTotalPages());
        map.put("currentUserId", currentUserId);
        map.put("superAdminId", SUPER_ADMIN_ID);

        logger.info("User list successfully returned");

        return "tableUser";
    }

    /**
     * Method that return empty form for creating user
     *
     * @param map - model that will be mapped to the creatingUser tile
     * @return name of the tile where controller will redirect user after this method
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String formAddUser(Map<String, Object> map,
                              @RequestParam(value = "groupId") Integer groupId) {

        map.put("user", new User());
        map.put("groupList", groupService.getGroupsByRole());
        map.put("currentGroup", new Group(groupId));
        map.put("categoryList", categoryService.findAllCategory());
        map.put("action", "/user/addUser");

        return "creatingUser";
    }

    /**
     * Method create user that will come from ui to the database
     *
     * @param user   - object of User class which comes from ui
     * @param result - contains information of correctness of the user which will
     *               come from ui due to entity restrictions
     * @return - redirects to the main page if the creating was successful and back
     * to the creatingUser page if bindingResult will have errors.
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult result,
                          Map<String, Object> map,
                          @RequestParam(value = "categoryIds", required = false) int[] categoryIds,
                          @RequestParam Integer groupSelect) {

        user.setGroup(new Group(groupSelect));
        user.setCategories(categoryService.getUsersCategories(categoryIds));

        //validation
        if (userService.isExist(user)) {
            result.rejectValue("userName", "error.user", "This login is already exist");
        }
        if (result.hasErrors()) {
            map.put("groupList", groupService.getGroupsByRole());
            map.put("currentGroup", user.getGroup());
            map.put("currentCategories", user.getCategories());
            map.put("user", user);
            map.put("categoryList", categoryService.findAllCategory());
            map.put("action", "/user/addUser");

            return "creatingUser";
        }  //end validation

        String password = SecurityUtil.generatePassword();   // generate password
        mailService.send(user.getUserName(), password); //sending on email
        user.setPassword(SecurityUtil.md5(password)); // hash password
        userService.addUser(user);

        logger.info("create new Person with login:{}", user.getUserName());

        return "redirect:/user?groupId=" + user.getGroup().getGroupId();
    }

    /**
     * Method delete user from database
     *
     * @param userId - id of User which you want to delete
     * @return - redirects to the main page after deleting user
     */
    @RequestMapping("/delete")
    public String removeUser(@RequestParam(value = "userId") Integer userId,
                             @RequestParam(value = "groupId") Integer groupId) {
        userService.removeUser(userId);
        logger.info("delete user with id{}", userId);

        return "redirect:/user?groupId=" + groupId;
    }

    /**
     * Method gets user by id that was set in url and maps him to the creatingUser-tile model
     *
     * @param userId - identifier of the user whose profile you want to edit
     * @param map    - map that will be mapped to the creatingUser tile
     * @return name of the tile where controller will redirect user after this method
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEditUser(Map<String, Object> map,
                               @RequestParam(value = "userId") Integer userId) {

        User user = userService.findUserById(userId);
        map.put("currentGroup", user.getGroup());
        map.put("currentCategories", user.getCategories());
        map.put("user", user);
        map.put("groupList", groupService.getGroupsByRole());
        map.put("categoryList", categoryService.findAllCategory());
        map.put("action", "/user/addEdit?userId=" + user.getUserId());

        return "creatingUser";
    }

    /**
     * Method updates user profile that will come from ui to the database
     *
     * @param user   - object of User class which comes from ui
     * @param result - contains information of correctness of the user which will come from ui
     *               due to entity restrictions
     * @return - redirects to the main page if the update was successful and back
     * to the creatingUser page if bindingResult will have errors.
     */
    @RequestMapping(value = "/addEdit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult result,
                           Map<String, Object> map,
                           @RequestParam Integer groupSelect,
                           @RequestParam(value = "categoryIds", required = false) int[] categoryIds) {

        User userInDB = userService.findUserById(user.getUserId());
        user.setGroup(new Group(groupSelect));
        user.setCategories(categoryService.getUsersCategories(categoryIds));

        //validation
        String userInDBUserName = userInDB.getUserName();// get name from DB
        if (userService.isExist(user) && !(userInDBUserName.equals(user.getUserName()))) {
            logger.error("Username already exists {}", user.getUserName());
            result.rejectValue("userName", "error.user", "This login is already exist");
        }
        if (result.hasErrors()) {
            map.put("currentGroup", user.getGroup());
            map.put("currentCategories", user.getCategories());
            map.put("user", user);
            map.put("groupList", groupService.getGroupsByRole());
            map.put("categoryList", categoryService.findAllCategory());
            map.put("action", "/user/addEdit?userId=" + user.getUserId());

            return "creatingUser";

        } //end validation

        if (!(userService.isExist(user)) && !(userInDBUserName.equals(user.getUserName()))) {
            String password = SecurityUtil.generatePassword(); // generate password
            mailService.send(user.getUserName(), password); //sending on email
            user.setPassword(SecurityUtil.md5(password)); // hash password
        } else {
            user.setPassword(userInDB.getPassword());
        }

        userService.addUser(user);
        logger.info("edit person and save with login:{}" + user.getUserName());

        return "redirect:/user?groupId=" + user.getGroup().getGroupId();
    }

    /**
     * This method get users JSON by group id
     */
    @RequestMapping(value = "/getUsersByGroup", params = "id")
    @ResponseBody
    public List<User> getUsersByCategoryId(@RequestParam("id") Integer id) {
        return userService.findUserByGroup(
                groupService.findGroupById(id));
    }
}