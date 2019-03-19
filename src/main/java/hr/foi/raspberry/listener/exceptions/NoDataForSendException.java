package hr.foi.raspberry.listener.exceptions;

public class NoDataForSendException extends Exception {

    public NoDataForSendException() {
    }

    public NoDataForSendException(String message) {
        super(message);
    }
}
