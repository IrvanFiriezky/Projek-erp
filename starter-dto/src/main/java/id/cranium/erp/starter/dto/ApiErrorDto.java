package id.cranium.erp.starter.dto;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class ApiErrorDto {
    
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrorDto() {
        super();
    }

    public ApiErrorDto(final HttpStatus status, final String message, final List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorDto(final HttpStatus status, final String message, final String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
