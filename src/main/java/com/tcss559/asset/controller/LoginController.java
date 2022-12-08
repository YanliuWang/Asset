package com.tcss559.asset.controller;

import com.tcss559.asset.models.User;
import com.tcss559.asset.models.dto.ResponseDto;
import com.tcss559.asset.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yanliu
 * @create 2022-12-07-6:25 PM
 */
@Controller
@CrossOrigin
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping("/login")
    public ResponseDto login(HttpServletRequest request, HttpServletResponse response, User user) {
        return loginService.login(request, response, user);
    }

    /**
     * register
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/register")
    public ResponseDto register(User user) {
        return loginService.register(user);
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/logout")
    public ResponseDto logout(HttpServletRequest request) {
        return loginService.logout(request);
    }




}
