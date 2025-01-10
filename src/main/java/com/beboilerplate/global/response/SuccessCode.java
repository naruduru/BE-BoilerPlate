package com.beboilerplate.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    ;


    private final int status;
    private final String code;
    private final String message;
}
