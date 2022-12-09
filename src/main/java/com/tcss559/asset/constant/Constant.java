package com.tcss559.asset.constant;

import java.util.Arrays;
import java.util.List;

public class Constant {

    public static String AUTH_HEADER = "Authorization";

    public static String TOKEN = "TOKEN";

    public static List<String> NONE_PERMISSION_RES = Arrays.asList(
            "/login/login",
            "/login/register"
    );

}
