package com.beboilerplate.global.exception;

import com.beboilerplate.global.response.ErrorCode;

public class IllegalArgumentException extends BusinessException{
    public IllegalArgumentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
