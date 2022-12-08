package com.tcss559.asset.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author yanliu
 * @create 2022-12-08-12:28 AM
 */
@Data
public class LoginForm {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$")
    private String userName;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$")
    private String password;
}
