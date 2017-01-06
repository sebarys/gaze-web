package com.sebarys.gazeWebsite.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sebarys.gazeWebsite.model.dto.UserInfo;

@RestController
public class Controller {

    @RequestMapping("/")
    public ModelAndView index() {
        final String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        if(role.equals("ROLE_ADMIN") || role.equals("ROLE_USER"))
            return getUserIndex();
        return getLoginPage();
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
    public UserInfo getLoggedUsername() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Object[] userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray();
        boolean isUser = false;
        boolean isAdmin = false;
        if(userRoles.length != 0) {
            final String firstRole = userRoles[0].toString();
            if(firstRole.equals("ROLE_USER")) {
                isUser = true;
            } else {
                if(firstRole.equals("ROLE_ADMIN"))
                    isAdmin = true;
            }

            if(userRoles.length != 1) {
                final String secondRole = userRoles[1].toString();
                if(secondRole.equals("ROLE_USER")) {
                    isUser = true;
                } else {
                    if(secondRole.equals("ROLE_ADMIN"))
                        isAdmin = true;
                }
            }
        }
        return new UserInfo(user.getUsername(), isUser, isAdmin);
    }

    private ModelAndView getUserIndex() {
        return new ModelAndView("user/userIndex");
    }


}
