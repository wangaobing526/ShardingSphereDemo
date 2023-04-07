package com.bus.businessdiaryapp.util;

public enum ExceptionCode {
    SUCCESS("00000", "成功"),
    USER_EXCEPTION("A0000", "用户异常"),
    SYSTEM_EXCEPTION("B0000", "系统异常"),
    THIRD_PARTY_EXCEPTION("C0000", "第三方异常");

    private String code;
    private String message;

    private ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}