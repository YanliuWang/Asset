package com.tcss559.asset.service.impl;


import com.tcss559.asset.dao.UserDAO;
import com.tcss559.asset.models.LoginForm;
import com.tcss559.asset.models.Response;
import com.tcss559.asset.models.User;
import com.tcss559.asset.models.dto.ResponseDto;
import com.tcss559.asset.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginService implementation
 */
@Service("LoginService")
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDAO userDao;

    @Override
    public Response login(LoginForm form) {
        String userName = form.getUserName();

        User selectedUser = userDao.select(userName);

        if (selectedUser == null) {
            return Response.error("user does not exist!");
        }

        String password = form.getPassword();

        if (!selectedUser.getPassword().equals(password)) {
            return Response.error("username or password is wrong!");
        }


        return Response.ok();

    }

    /**
     * register
     *
     * @param user
     * @return
     */
    @Override
    public ResponseDto register(User user) {
        return null;
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    @Override
    public ResponseDto logout(HttpServletRequest request) {
        return null;
    }
}
