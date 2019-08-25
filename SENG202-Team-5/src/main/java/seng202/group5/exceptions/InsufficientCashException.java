package seng202.group5.exceptions;

public class InsufficientCashException extends Exception {
    public InsufficientCashException() {

    }

    public InsufficientCashException(String message) {
        super(message);
    }
}
