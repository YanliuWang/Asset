package com.tcss559.asset.dao;

import com.tcss559.asset.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
    public User selectOne(int userId);

    public User select(String userName);
}
