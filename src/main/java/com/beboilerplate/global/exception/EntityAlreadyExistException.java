package com.beboilerplate.global.exception;

import com.beboilerplate.global.response.ErrorCode;

public class EntityAlreadyExistException extends BusinessException{
    public EntityAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
