package com.sebarys.gazeWebsite.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class Controller {

    @RequestMapping("/")
    public ModelAndView index() {
        final String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        if(role.equals("ROLE_ADMIN"))
            return getAdminIndex();
        else if(role.equals("ROLE_USER"))
            return getUserIndex();
        return getLoginPage();
    }

    @RequestMapping("user")
    public ModelAndView getUserIndex() {
        return new ModelAndView("user/userIndex");
    }

    @RequestMapping("/admin")
    public ModelAndView getAdminIndex(){
        return new ModelAndView("admin/adminIndex");
    }

    @RequestMapping("/login")
    public ModelAndView getLoginPage(){
        return new ModelAndView("public/index");
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return getLoginPage();
    }

    @RequestMapping("/username")
    public String getLoggedUsername() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "{\"name\": \"" + user.getUsername() + "\"}";
    }


}
