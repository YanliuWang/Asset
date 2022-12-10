package com.tcss559.asset.dao;

import com.tcss559.asset.models.User;
import org.springframework.stereotype.Repository;

/**
 * @Description: user DAO
 * @Author: Xiaojie Li
 * @Date: 2022/12/9
 */
@Repository
public interface UserDAO {
    /**
     * select one user record
     *
     * @param userId
     * @return User
     */
    public User selectOne(int userId);

    /**
     * select user by userName
     *
     * @param userName
     * @return User
     */
    public User selectUser(String userName);

    /**
     * insert user
     *
     * @param user
     * @return User
     */
    public void insert(User user);

}
