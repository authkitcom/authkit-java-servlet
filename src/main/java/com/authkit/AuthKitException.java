package com.authkit;

public class AuthKitException extends RuntimeException {

    public AuthKitException() {
        super();
    }

    public AuthKitException(String message) {
        super(message);
    }

    public AuthKitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthKitException(Throwable cause) {
        super(cause);
    }

}
