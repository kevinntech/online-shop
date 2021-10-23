package me.kevinntech.modules.main.exception;

public class NotValidArgumentException extends RuntimeException {

    public NotValidArgumentException() {
    }

    public NotValidArgumentException(String message) {
        super(message);
    }

    public NotValidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidArgumentException(Throwable cause) {
        super(cause);
    }

    public NotValidArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
