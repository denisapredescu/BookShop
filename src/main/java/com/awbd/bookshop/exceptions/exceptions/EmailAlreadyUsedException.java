package com.awbd.bookshop.exceptions.exceptions;

import com.awbd.bookshop.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyUsedException extends BaseException {
    public EmailAlreadyUsedException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
