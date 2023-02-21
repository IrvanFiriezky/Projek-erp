package id.cranium.erp.starter.exception;

import java.lang.RuntimeException;

public class ClientException extends RuntimeException {

    private int code;

    public ClientException() {
        super();
    }

    public ClientException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
}
