package com.awbd.bookshop.exceptions.exceptions;

import com.awbd.bookshop.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotLoggedInException extends BaseException {
    public UserNotLoggedInException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
