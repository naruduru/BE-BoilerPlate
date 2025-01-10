package com.beboilerplate.global.exception;

import com.beboilerplate.global.response.ErrorCode;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
