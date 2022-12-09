package com.tcss559.asset.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yanliu
 * @create 2022-12-08-10:48 PM
 */

@Data
public class UserResponse implements Serializable {

    private int userId;

    private String userName;

    private String password;

    private String role;

    private String salt;

    private String email;

    private String token;
}
