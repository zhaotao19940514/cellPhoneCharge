package com.yxx.integral_exchange.vo;

/**
 * 响应结果码
 *
 * @author jacky
 * @date 2020/6/17
 */
public enum ResultCode {
    SUCCESS(20000, "操作成功"),
    FAILED(99999, "操作失败"),
    VALIDATE_FAILED(50013, "参数检验失败"),
    UNAUTHORIZED(50014, "暂未登录或token已经过期"),
    FORBIDDEN(50015, "没有相关权限"),
    SUCESS_OLD(200, "操作成功");
    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
