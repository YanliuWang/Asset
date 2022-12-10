package com.tcss559.asset.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: user model
 * @Author: Xiaojie Li
 * @Date: 2022/12/9
 */
public class User {
    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String role;

    @Getter
    @Setter
    private String salt;

    @Getter
    @Setter
    private String email;
}
