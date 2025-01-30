package com.beboilerplate.global.exception;

import com.beboilerplate.global.response.ErrorCode;

public class FileUploadErrorException extends BusinessException {

    public FileUploadErrorException() {
        super(ErrorCode.FILE_UPLOAD_ERROR);
    }
}
