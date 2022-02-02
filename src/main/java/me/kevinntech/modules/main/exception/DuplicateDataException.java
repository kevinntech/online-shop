package me.kevinntech.modules.main.exception;

public class DuplicateDataException extends RuntimeException{

    public DuplicateDataException() {
    }

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateDataException(Throwable cause) {
        super(cause);
    }

    public DuplicateDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
