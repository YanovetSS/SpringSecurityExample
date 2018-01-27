package com.ukraine.springsecurity.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HelloWorldController {

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)//урл по якому доступний в браузері
    public String homePage(ModelMap model) {
        // атрибут нейм- змінна , атрибут валью - те значення яке буде виводитись якщо викликати змінну
        model.addAttribute("greeting", "Hi,welcome to mysite");
        return "welcome";//jsp
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin";//jsp
    }

    @RequestMapping(value = {"/db"},method = RequestMethod.GET)
    public String dbaPage(ModelMap modell){
        modell.addAttribute("user",getPrincipal());
        return "dba";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "welcome";
    }

    @RequestMapping(value = "/Access_Denied",method = RequestMethod.GET)
    public String acessDenied(ModelMap model){
        model.addAttribute("user",getPrincipal());
        return "accessDenied";
    }

    private String getPrincipal() {
        String userName=null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){
            userName = ((UserDetails) principal).getUsername();
        }else {
            userName= principal.toString();
        }
        return userName;
    }

}
