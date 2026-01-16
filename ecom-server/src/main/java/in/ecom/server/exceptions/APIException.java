package in.ecom.server.exceptions;

public class APIException extends RuntimeException {

    private static final long serialVersionUTD = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}