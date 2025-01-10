package com.beboilerplate.domain.member.exception;

import com.beboilerplate.global.exception.BusinessException;
import com.beboilerplate.global.response.ErrorCode;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}

