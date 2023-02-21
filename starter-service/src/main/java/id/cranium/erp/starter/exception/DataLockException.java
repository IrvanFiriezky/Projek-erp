package id.cranium.erp.starter.exception;

public class DataLockException extends ClientException {
    
    public DataLockException() {
        super();
    }

    public DataLockException(String message) {
        super(message, 409);
    }

    public DataLockException(String message, int code) {
        super(message, code);
    }
}
