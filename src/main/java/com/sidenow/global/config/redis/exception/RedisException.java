package com.sidenow.global.config.redis.exception;

import com.sidenow.global.exception.ApplicationException;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.http.HttpStatus;

public class RedisException extends ApplicationException {
    protected RedisException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
