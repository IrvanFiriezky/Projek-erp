package id.cranium.erp.starter.exception;

import java.lang.RuntimeException;

public class ServerException extends RuntimeException {

    private int code;
    
    public ServerException() {
        super();
    }

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
}
