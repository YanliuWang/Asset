package com.tcss559.asset.util;

import com.alibaba.fastjson.JSONObject;
import com.tcss559.asset.constant.Constant;
import com.tcss559.asset.enums.RoleEnums;
import com.tcss559.asset.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserUtil {

    /**
     * 加盐参数
     */
    public final String hashAlgorithmName = "MD5";

    /**
     * 循环次数
     */
    public final int hashIterations = 2;

    @Resource
    private RedisUtil redisUtils;


    /**
     * md5
     *
     * @param credentials 密码
     * @param saltSource  密码盐
     * @return
     */
    public String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
    }

    /**
     * random salt
     *
     * @param length
     * @return
     */
    public String getRandomSalt(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }


    public User getCurrentUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader(Constant.AUTH_HEADER);
        if (StringUtils.isNotBlank(token)) {
            String userStr = (String) redisUtils.get(token);
            if (StringUtils.isNotBlank(userStr)) {
                return JSONObject.parseObject(userStr, User.class);
            }
        }
        return null;
    }


    public Boolean isAdmin() {
        User user = getCurrentUser();
        return RoleEnums.ADMIN.getCode().equals(user.getRole());
    }

    /**
     * get current user by token
     *
     * @return
     */
    public Integer getCurrentUserId() {
        User user = getCurrentUser();
        return user.getUserId();
    }

}

