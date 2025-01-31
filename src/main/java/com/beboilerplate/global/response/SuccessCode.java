package com.beboilerplate.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Auth
    SIGN_UP_SUCCESS(201, "AU001", "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(200, "AU002", "로그인에 성공하였습니다."),

    // Room
    CREATE_ROOM_SUCCESS(201, "R001", "채팅방 생성에 성공하였습니다."),
    GET_MY_ROOMS_SUCCESS(200, "R002", "내 채팅방 목록 조회에 성공하였습니다."),

    // Chat
    GET_CHATS_SUCCESS(200, "C001", "채팅 목록 조회에 성공하였습니다."),

    // Post
    REGISTER_POST_SUCCESS(201, "P001", "게시글 등록에 성공하였습니다."),
    GET_POSTS_SUCCESS(200, "P002", "게시글 목록 조회에 성공하였습니다."),
    GET_POST_DETAIL_SUCCESS(200, "P003", "게시글 상세 조회에 성공하였습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
