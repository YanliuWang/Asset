package com.tcss559.asset.service.impl;


import com.tcss559.asset.dao.UserDAO;
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
    public ResponseDto login(HttpServletRequest request, HttpServletResponse response, User user) {
        User user1 = userDao.selectOne(1);

        return ResponseDto.success(user1);

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
