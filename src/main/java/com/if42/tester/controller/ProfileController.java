package com.if42.tester.controller;

import com.if42.tester.entity.User;
import com.if42.tester.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * This controller using for showing information about user
 * and editing users profile.
 *
 * @author Kocjuba Gregory
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    /** This method return page with information about user */
    @RequestMapping(value="profile", method = RequestMethod.GET)
    public String profile(Map<String, Object> map,
                          Principal principal ) {

        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        map.put("user", user);

        return "profile";
    }

    /** This method using for update information about user */
    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public String edit(@ModelAttribute("user") User editUser,
                       Principal principal) {

        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        user.setName(editUser.getName());
        user.setSurname(editUser.getSurname());
        user.setMiddleName(editUser.getMiddleName());
        userService.addUser(user);

        return "redirect:/profile";
    }
}
