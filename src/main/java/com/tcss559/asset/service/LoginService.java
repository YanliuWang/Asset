package com.tcss559.asset.service;


import com.alibaba.fastjson.JSONObject;
import com.tcss559.asset.dao.UserDAO;
import com.tcss559.asset.enums.RoleEnums;
import com.tcss559.asset.models.User;
import com.tcss559.asset.models.UserResponse;
import com.tcss559.asset.models.Response;
import com.tcss559.asset.util.UserUtil;
import com.tcss559.asset.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginService implementation
 */
@Service("LoginService")
public class LoginService {

    @Resource
    private UserDAO userDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserUtil userUtil;

    public Response login(HttpServletRequest request, HttpServletResponse response, User user) {
        String userName = user.getUserName();

        User selectedUser = userDao.selectUser(userName);

        if (selectedUser == null) {
            return Response.error("user does not exist!");
        }

        String salt = selectedUser.getSalt();
        String password = userUtil.md5(user.getPassword(), salt);

        String realPass = selectedUser.getPassword();
        if (!realPass.equals(password)) {
            return Response.error("username or password is wrong!");
        }


        String token = "TCSS559" + "-" + userUtil.getRandomSalt(16) + "-" + user.getUserId();
        redisUtil.set(token, JSONObject.toJSONString(user), 24 * 60 * 60L);
        response.setHeader("Authorization", token);

        UserResponse userResponse = new UserResponse();
        userResponse.setToken(token);
        BeanUtils.copyProperties(user, userResponse);

        return Response.success(userResponse);

    }

    /**
     * register
     *
     * @param user
     * @return
     */
    public Response register(User user) {
        String userName = user.getUserName();

        User selectedUser = userDao.selectUser(userName);

        if (selectedUser != null) {
            return Response.error("user already exists!");
        }

        User registeredUser = new User();
        BeanUtils.copyProperties(user, registeredUser, "password");

        String salt = userUtil.getRandomSalt(5);
        String password = userUtil.md5(user.getPassword(), salt);

        registeredUser.setSalt(salt);
        registeredUser.setRole(RoleEnums.NORMAL.getCode());
        registeredUser.setPassword(password);

        userDao.insert(registeredUser);


        return Response.success();
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public Response logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        redisUtil.remove(token);

        return Response.success();
    }
}
