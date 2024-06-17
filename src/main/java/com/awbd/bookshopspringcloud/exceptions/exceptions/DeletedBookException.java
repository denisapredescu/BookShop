package com.awbd.bookshopspringcloud.exceptions.exceptions;

import com.awbd.bookshopspringcloud.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class DeletedBookException extends BaseException {
    public DeletedBookException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
