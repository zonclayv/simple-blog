package by.haidash.blog.server.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

/**
 * Created by haidash on 06.04.17.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${application.stacktrace.include}")
    private boolean includeStacktrace;

    @Autowired
    private ErrorAttributes errorAttributes;

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleException(
            final RuntimeException ex,
            final WebRequest request) {

        final ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .setStatus(INTERNAL_SERVER_ERROR.value())
                .setMessage(ex.getMessage())
                .setTimestamp(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        final List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        final ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .setStatus(status.value())
                .setMessage(ex.getMessage())
                .setErrors(errors)
                .setTimestamp(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, exceptionResponse, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        final ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .setStatus(BAD_REQUEST.value())
                .setMessage(ex.getMessage())
                .setError(ex.getParameterName() + " parameter is missing")
                .setTimestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            final ConstraintViolationException ex,
            final WebRequest request) {

        final List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " +
                    violation.getMessage());
        }

        final ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .setStatus(BAD_REQUEST.value())
                .setMessage(ex.getMessage())
                .setErrors(errors)
                .setTimestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            final MethodArgumentTypeMismatchException ex,
            final WebRequest request) {

        final ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .setStatus(BAD_REQUEST.value())
                .setMessage(ex.getMessage())
                .setError(ex.getName() + " should be of type " + ex.getRequiredType().getName())
                .setTimestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            final NoHandlerFoundException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        final ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .setStatus(NOT_FOUND.value())
                .setMessage(ex.getMessage())
                .setError("No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL())
                .setTimestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        final ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .setStatus(METHOD_NOT_ALLOWED.value())
                .setMessage(ex.getMessage())
                .setError(ex.getMethod()+" method is not supported for this request.")
                .setTimestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            final HttpMediaTypeNotSupportedException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        final ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .setStatus(UNSUPPORTED_MEDIA_TYPE.value())
                .setMessage(ex.getMessage())
                .setError(ex.getContentType() + " media type is not supported.")
                .setTimestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), UNSUPPORTED_MEDIA_TYPE);
    }
}

