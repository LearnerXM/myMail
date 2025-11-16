package com.judecodes.mailbase.exception;

public class RemoteCallException extends SystemException{

    public RemoteCallException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
