package com.tcss559.asset.models;


import lombok.Data;

/**
 * PageResponseDto
 */
@Data
public class PageResponse {

    public static final String DEFAULT_SUCCESS_MESSAGE = "success";

    public static final String DEFAULT_ERROR_MESSAGE = "something error";

    public static final String KEY_MISS_OR_NOT_CORRECT = "token is missing.";

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

    /**
     * count
     */
    private long count;



    public PageResponse(Boolean success, Integer code, String message, Object data, long count) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public static PageResponse success(Object t, long count) {
        return new PageResponse(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, t, count);
    }

    public static PageResponse success() {
        return new PageResponse(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null, 0);
    }


    public static PageResponse error(String message) {
        return new PageResponse(false, DEFAULT_ERROR_CODE, message, null, 0);
    }


}
