package com.tcss559.asset.controller;

import com.tcss559.asset.models.User;
import com.tcss559.asset.models.Response;
import com.tcss559.asset.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author yanliu
 * @create 2022-12-07-6:25 PM
 */
@Controller
@CrossOrigin
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "/login", method = POST, produces = "application/json")
    public Response login(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        return loginService.login(request, response, user);
    }

    /**
     * register
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = POST)
    public Response register(@RequestBody User user) {
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
    public Response logout(HttpServletRequest request) {
        return loginService.logout(request);
    }




}
