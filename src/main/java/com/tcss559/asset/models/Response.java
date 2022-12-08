package com.tcss559.asset.models;

import lombok.Data;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanliu
 * @create 2022-12-08-12:24 AM
 */

@Data
public class Response extends HashMap<String, Object> {
    public Response() {
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }

    public static Response error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static Response error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Response error(int code, String msg) {
        Response r = new Response();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Response ok(String msg) {
        Response r = new Response();
        r.put("msg", msg);
        return r;
    }

    public static Response ok(Map<String, Object> map) {
        Response r = new Response();
        r.putAll(map);
        return r;
    }

    public static Response ok() {
        return new Response();
    }

    public Response put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

