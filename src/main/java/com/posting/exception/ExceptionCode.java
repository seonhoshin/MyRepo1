package com.posting.exception;

import lombok.Getter;


public enum ExceptionCode {
    NOT_FOUND(404, "Not Found!!");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}
