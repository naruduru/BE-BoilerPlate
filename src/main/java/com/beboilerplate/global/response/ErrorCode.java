package com.beboilerplate.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(500, "G001", "내부 서버 오류입니다."),
    METHOD_NOT_ALLOWED(405, "G002", "허용되지 않은 HTTP method입니다."),
    INPUT_VALUE_INVALID(400, "G003", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "G004", "입력 타입이 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(400, "G005", "request message body가 없거나, 값 타입이 올바르지 않습니다."),
    HTTP_HEADER_INVALID(400, "G006", "request header가 유효하지 않습니다."),

    // Auth
    INVALID_TOKEN(400, "AU001", "유효하지 않은 토큰입니다."),
    NOT_EXPIRED_ACCESS_TOKEN(400, "AU002", "만료되지 않은 Access Token입니다."),
    FORBIDDEN(403, "AU003", "접근할 수 있는 권한이 없습니다."),
    EXPIRED_OR_PREVIOUS_REFRESH_TOKEN(403, "AU004", "만료되었거나 이전에 발급된 Refresh Token입니다."),
    ACCESS_DENIED(401, "AU005", "유효한 인증 정보가 아닙니다."),
    EXPIRED_ACCESS_TOKEN(401, "AU006", "Access Token이 만료되었습니다. 토큰을 재발급해주세요"),

    // Member
    MEMBER_NOT_FOUND(404, "M001", "존재하지 않는 유저입니다."),
    ALREADY_EXIST_NICKNAME(400, "M002", "중복된 닉네임입니다."),
    ALREADY_EXIST_EMAIL(400, "M003", "이미 가입된 이메일입니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
