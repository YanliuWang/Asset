package com.tcss559.asset.exception;

import lombok.Data;

/**
 * @author yanliu
 * @create 2022-12-08-12:39 AM
 */
@Data
public class AssetException extends RuntimeException{
    private String msg;
    private int code = 500;

    public AssetException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AssetException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public AssetException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public AssetException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
