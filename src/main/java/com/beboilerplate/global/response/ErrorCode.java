package com.beboilerplate.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(500, "GLOBAL001", "내부 서버 오류입니다."),
    METHOD_NOT_ALLOWED(405, "GLOBAL002", "허용되지 않은 HTTP method입니다."),
    INPUT_VALUE_INVALID(400, "GLOBAL003", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "GLOBAL004", "입력 타입이 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(400, "GLOBAL005", "request message body가 없거나, 값 타입이 올바르지 않습니다."),
    HTTP_HEADER_INVALID(400, "GLOBAL006", "request header가 유효하지 않습니다."),
    FILE_UPLOAD_ERROR(400, "GLOBAL007", "파일 업로드를 실패했습니다."),
    FILE_EXTENSION_INVALID(400, "GLOBAL008", "지원하지 않는 파일 형식입니다."),

    // Auth
    INVALID_TOKEN(400, "AUTH001", "유효하지 않은 토큰입니다."),
    NOT_EXPIRED_ACCESS_TOKEN(400, "AUTH002", "만료되지 않은 Access Token입니다."),
    FORBIDDEN(403, "AUTH003", "접근할 수 있는 권한이 없습니다."),
    EXPIRED_OR_PREVIOUS_REFRESH_TOKEN(403, "AUTH004", "만료되었거나 이전에 발급된 Refresh Token입니다."),
    ACCESS_DENIED(401, "AUTH005", "유효한 인증 정보가 아닙니다."),
    EXPIRED_ACCESS_TOKEN(401, "AUTH006", "Access Token이 만료되었습니다. 토큰을 재발급해주세요"),
    PASSWORD_MISMATCH(400, "AUTH007", "패스워드가 일치하지 않습니다"),

    // Member
    MEMBER_NOT_FOUND(404, "MEMBER001", "존재하지 않는 유저입니다."),
    ALREADY_EXIST_NICKNAME(400, "MEMBER002", "중복된 닉네임입니다."),
    ALREADY_EXIST_EMAIL(400, "MEMBER003", "이미 가입된 이메일입니다."),

    // Chat
    ROOM_NOT_FOUND(404, "CHAT001", "존재하지 않는 채팅방입니다."),
    ROOM_TYPE_NOT_FOUND(404, "CHAT002", "존재하지 않는 채팅방 타입입니다."),

    // Post
    POST_NOT_FOUND(404, "POST001", "존재하지 않는 게시글입니다."),
    POST_NOT_BELONG_TO_MEMBER(400, "POST002", "게시글 작성자가 아닙니다."),
    
    // Comment
    COMMENT_NOT_FOUND(404, "COMMENT001", "존재하지 않는 댓글입니다."),
    COMMENT_NOT_BELONG_TO_MEMBER(400, "COMMENT002", "댓글 작성자가 아닙니다."),
    COMMENT_NOT_BELONG_TO_POST(400, "COMMENT003", "댓글이 게시글에 속해있지 않습니다."),

    ;

    private final int status;
    private final String code;
    private final String message;
}
