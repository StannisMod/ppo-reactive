package com.github.stannismod.reactive.http;

public class UsernameException extends RuntimeException {

    public UsernameException() {
    }

    public UsernameException(final String message) {
        super(message);
    }

    public UsernameException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UsernameException(final Throwable cause) {
        super(cause);
    }

    public UsernameException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
