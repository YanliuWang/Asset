package com.tcss559.asset.models;

import lombok.Getter;
import lombok.Setter;

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
    private boolean isAdmin;

    @Getter
    @Setter
    private String email;
}
