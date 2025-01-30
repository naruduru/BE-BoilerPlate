package com.beboilerplate.global.exception;

import com.beboilerplate.global.response.ErrorCode;

public class FileExtensionInvalidException extends BusinessException {

    public FileExtensionInvalidException() {
        super(ErrorCode.FILE_EXTENSION_INVALID);
    }
}
