package com.tcss559.asset.models;


import lombok.Data;

/**
 * ResponseDto
 */
@Data
public class Response {

    public static final String DEFAULT_SUCCESS_MESSAGE = "success";

    public static final String DEFAULT_ERROR_MESSAGE = "something error";

    public static final String KEY_MISS_OR_NOT_CORRECT = "Token is Required";

    public static final Integer DEFAULT_SUCCESS_CODE = 200;

    public static final Integer DEFAULT_ERROR_CODE = 500;

    /**
     * success
     */
    private Boolean success;

    /**
     * code
     */
    private Integer code;

    /**
     * message
     */
    private String message;

    /**
     * data
     */
    private Object data;

    public Response() {
    }

    public Response(Boolean success, Integer code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response success(Object t) {
        return new Response(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, t);
    }

    public static Response success() {
        return new Response(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null);
    }


    public static Response error(String message) {
        return new Response(false, DEFAULT_ERROR_CODE, message, null);
    }


}
