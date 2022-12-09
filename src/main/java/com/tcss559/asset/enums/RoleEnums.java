package com.tcss559.asset.enums;


public enum RoleEnums {

    ADMIN("admin", "admin"),
    NORMAL("normal", "normal user");

    private String code;

    private String name;

    RoleEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
