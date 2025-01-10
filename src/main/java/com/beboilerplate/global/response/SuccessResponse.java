package com.beboilerplate.global.response;

import lombok.Getter;

@Getter
public class SuccessResponse {

    private final int status; // HTTP 상태코드
    private final String code; // Business 코드
    private final String message; // 응답 메세지
    private final Object data; // 응답 데이터

    public SuccessResponse(SuccessCode successCode, Object data) {
        this.status = successCode.getStatus();
        this.code = successCode.getCode();
        this.message = successCode.getMessage();
        this.data = data;
    }

    public static SuccessResponse of(SuccessCode successCode, Object data) {
        return new SuccessResponse(successCode, data);
    }

    public static SuccessResponse of(SuccessCode successCode) {
        return new SuccessResponse(successCode, "");
    }
}
