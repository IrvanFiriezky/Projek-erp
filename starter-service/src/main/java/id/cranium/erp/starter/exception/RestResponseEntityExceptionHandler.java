package id.cranium.erp.starter.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.RuntimeException;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.starter.dto.ApiErrorDto;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResourceBundleMessageSource starterMessageSource;

    private String STARTER_DATA_NOTEXISTS_EXCEPTION = "starter.data.notexists.exception";
    private String STARTER_LOCK_EXCEPTION = "starter.lock.exception";
	private String STARTER_UNAUTHORIZED_EXCEPTION = "starter.unauthorized.exception";
    private String STARTER_FORBIDDEN_EXCEPTION = "starter.forbidden.exception";
    private String STARTER_UNHANDLED_EXCEPTION = "starter.unhandled.exception";

    public RestResponseEntityExceptionHandler(ResourceBundleMessageSource starterMessageSource) {
        this.starterMessageSource = starterMessageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        log.info("MethodArgumentNotValidException: " + errors);
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiErrorDto, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { DataNotFoundException.class })
    protected ResponseEntity<Object> handleConflict(DataNotFoundException ex, WebRequest request) {
        String error = starterMessageSource.getMessage(STARTER_DATA_NOTEXISTS_EXCEPTION, null, request.getLocale());
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { DataLockException.class, ObjectOptimisticLockingFailureException.class, PessimisticLockingFailureException.class })
    protected ResponseEntity<Object> handleConflict(DataLockException ex, WebRequest request) {
        String error = starterMessageSource.getMessage(STARTER_LOCK_EXCEPTION, null, request.getLocale());
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.CONFLICT, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { ClientException.class })
    protected ResponseEntity<Object> handleConflict(ClientException ex, WebRequest request) {
        ApiErrorDto apiErrorDto;
        try {
            apiErrorDto = new ObjectMapper().readValue(ex.getMessage(), ApiErrorDto.class);
        } catch (IOException jsonException) {
            apiErrorDto = new ApiErrorDto(HttpStatus.valueOf(ex.getCode()), ex.getLocalizedMessage(), ex.getMessage());
        }
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), apiErrorDto.getStatus(), request);
    }

    @ExceptionHandler(value = { ServerException.class })
    protected ResponseEntity<Object> handleConflict(ServerException ex, WebRequest request) {
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.valueOf(ex.getCode()), ex.getLocalizedMessage(), ex.getMessage());
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), apiErrorDto.getStatus(), request);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Object> handleConflict(ConstraintViolationException ex, WebRequest request) {
        final List<String> errors = new ArrayList<String>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        log.info("ConstraintViolationException: " + errors);
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    protected ResponseEntity<Object> handleConflict(AccessDeniedException ex, WebRequest request) {
        String error = starterMessageSource.getMessage(STARTER_FORBIDDEN_EXCEPTION, null, request.getLocale());
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = { AuthenticationException.class })
    protected ResponseEntity<Object> handleConflict(AuthenticationException ex, WebRequest request) {
        String error = starterMessageSource.getMessage(STARTER_UNAUTHORIZED_EXCEPTION, null, request.getLocale());
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String error = starterMessageSource.getMessage(STARTER_UNHANDLED_EXCEPTION, null, request.getLocale());
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
        String error = starterMessageSource.getMessage(STARTER_UNHANDLED_EXCEPTION, null, request.getLocale());
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return handleExceptionInternal(ex, apiErrorDto, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}