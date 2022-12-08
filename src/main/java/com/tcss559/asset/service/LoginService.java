package com.tcss559.asset.service;

import com.tcss559.asset.models.dto.ResponseDto;
import com.tcss559.asset.models.param.UserParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: login service
 */
public interface LoginService {

    /**
     * login
     *
     * @param request
     * @param response
     * @param userParam
     * @return
     */
    ResponseDto login(HttpServletRequest request, HttpServletResponse response, UserParam userParam);

    /**
     * register
     *
     * @param userParam
     * @return
     */
    ResponseDto register(UserParam userParam);

    /**
     * logout
     *
     * @param request
     * @return
     */
    ResponseDto logout(HttpServletRequest request);


}
