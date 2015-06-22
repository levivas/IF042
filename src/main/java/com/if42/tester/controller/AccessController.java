package com.if42.tester.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 This controller is used for login
 *
 * @author Kocjuba Gregory
 */
@Controller
@RequestMapping
public class AccessController {

    /** This method is used for login */
    @RequestMapping("/login")
    public String login(Map<String, Object> map,
                        @RequestParam(value = "message", required=false) String message,
                        @RequestParam(value = "color", required=false) String color) {
        map.put("message", message);
        map.put("color", color);
        return "access/login";
    }

    /** This method showing message if user try to get access to forbidden page */
    @RequestMapping(value = "/denied")
    public String denied() {
        return "access/denied";
    }

    /** This method showing message if login failure */
    @RequestMapping(value = "/login/failure")
    public String loginFailure() {
        String message = "Login Failure!";
        String color= "red";
        return "redirect:/login?message="+message+"&color="+color;
    }

    /** This method redirecting on login page when user logout */
    @RequestMapping(value = "/logout/success")
    public String logoutSuccess() {
        String message = "Logout Success!";
        String color= "blue";
        return "redirect:/login?message="+message + "&color="+color;
    }

    /** This method redirected on availableTest */
    @RequestMapping
    public String getHomePage() {
        return "redirect:/availableTest";
    }

    @RequestMapping("/team")
    public String showTeam() {
        return "team";
    }
}