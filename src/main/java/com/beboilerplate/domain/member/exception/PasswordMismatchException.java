package com.beboilerplate.domain.member.exception;

import com.beboilerplate.global.exception.BusinessException;
import com.beboilerplate.global.response.ErrorCode;

public class PasswordMismatchException extends BusinessException {

    public PasswordMismatchException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}

