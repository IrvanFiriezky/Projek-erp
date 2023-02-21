package id.cranium.erp.starter.exception;

public class DataNotFoundException extends ClientException {

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message, 404);
    }

    public DataNotFoundException(String message, int code) {
        super(message, code);
    }
}
