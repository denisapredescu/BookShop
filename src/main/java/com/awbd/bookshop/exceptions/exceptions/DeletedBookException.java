package com.awbd.bookshop.exceptions.exceptions;

import com.awbd.bookshop.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class DeletedBookException extends BaseException {
    public DeletedBookException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
