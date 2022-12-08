package com.tcss559.asset.service.impl;


import com.tcss559.asset.models.dto.ResponseDto;
import com.tcss559.asset.models.param.UserParam;
import com.tcss559.asset.service.LoginService;
import com.tcss559.asset.service.result.UserResult;
import org.springframework.beans.BeanUtils;
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
    private UserMapper userMapper;

    @Resource
    private UserKit userKit;


    @Override
    public ResponseDto login(HttpServletRequest request, HttpServletResponse response, UserParam userParam) {
        String account = userParam.getAccount();
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("status", 1);
        List<UserEntity> list = userMapper.selectList(queryWrapper);

        if (list.size() == 0) {
            return ResponseDto.error("account is not exist");
        }
        UserEntity userEntity = list.get(0);

        String salt = userEntity.getSalt();
        String password = userKit.md5(userParam.getPassword(), salt);

        String realPassword = userEntity.getPassword();
        if (!password.equals(realPassword)) {
            return ResponseDto.error("password is not correct");
        }

        String token = "TCSS559 " + userKit.getRandomSalt(16);
        redisUtil.set(token, JSONObject.toJSONString(userEntity), 24 * 60 * 60L);
        response.setHeader("Authorization", token);

        UserResult userResult = new UserResult();
        userResult.setToken(token);
        BeanUtils.copyProperties(userEntity, userResult);
        return ResponseDto.success(userResult);

    }

    @Override
    public ResponseDto register(UserParam userParam) {
        String account = userParam.getAccount();
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("status", 1);
        List<UserEntity> list = userMapper.selectList(queryWrapper);

        if (list.size() > 0) {
            return ResponseDto.error("account is already exist");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userParam, userEntity, "password");
        String salt = userKit.getRandomSalt(5);
        String password = userKit.md5(userParam.getPassword(), salt);
        userEntity.setSalt(salt);
        userEntity.setRole(RoleEnums.NORMAL.getCode());
        userEntity.setPassword(password);
        userEntity.setStatus(1);
        userMapper.insert(userEntity);

        sendNotice(userEntity);

        return ResponseDto.success();

    }

    @Override
    public ResponseDto logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        redisUtil.remove(token);
        return ResponseDto.success();
    }


    /**
     * send notice via sms and email
     *
     * @param userEntity
     */
    private void sendNotice(UserEntity userEntity) {
        NoticeParam param = new NoticeParam();
        param.setEmailAddress(userEntity.getEmail());
        param.setPhone(userEntity.getPhone());
        param.setUserName(userEntity.getName());
        smsService.sendWelcomeSms(param);
        emailService.sendWelcome(param);
    }
}
