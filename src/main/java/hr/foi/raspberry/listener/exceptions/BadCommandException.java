package hr.foi.raspberry.listener.exceptions;

public class BadCommandException extends Exception {

    public BadCommandException() {
        super();
    }

    public BadCommandException(String message) {
        super(message);
    }
}
