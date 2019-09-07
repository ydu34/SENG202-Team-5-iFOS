package seng202.group5.exceptions;

public class NoPastOrderException extends Exception {
    public NoPastOrderException() {

    }

    public NoPastOrderException(String message) {
        super(message);
    }
}
