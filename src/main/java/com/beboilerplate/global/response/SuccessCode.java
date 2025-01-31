package com.beboilerplate.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    // Auth
    SIGN_UP_SUCCESS(201, "AUTH001", "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(200, "AUTH002", "로그인에 성공하였습니다."),

    // Room
    CREATE_ROOM_SUCCESS(201, "ROOM001", "채팅방 생성에 성공하였습니다."),
    GET_MY_ROOMS_SUCCESS(200, "ROOM002", "내 채팅방 목록 조회에 성공하였습니다."),

    // Chat
    GET_CHATS_SUCCESS(200, "CHAT001", "채팅 목록 조회에 성공하였습니다."),

    // Post
    ADD_POST_SUCCESS(201, "POST001", "게시글 등록에 성공하였습니다."),
    GET_POSTS_SUCCESS(200, "POST002", "게시글 목록 조회에 성공하였습니다."),
    GET_POST_DETAIL_SUCCESS(200, "POST003", "게시글 상세 조회에 성공하였습니다."),
    DELETE_POST_SUCCESS(200, "POST004", "게시글 삭제에 성공하였습니다."),
    UPDATE_POST_SUCCESS(200, "POST005", "게시글 수정에 성공하였습니다."),

    // Comment
    ADD_COMMENT_SUCCESS(201, "COMMENT001", "댓글 등록에 성공하였습니다."),
    ADD_REPLY_SUCCESS(201, "COMMENT002", "대댓글 등록에 성공하였습니다."),
    GET_COMMENTS_SUCCESS(200, "COMMENT003", "댓글 목록 조회에 성공하였습니다."),
    DELETE_COMMENT_SUCCESS(200, "COMMENT004", "댓글 삭제에 성공하였습니다."),
    UPDATE_COMMENT_SUCCESS(200, "COMMENT005", "댓글 수정에 성공하였습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
