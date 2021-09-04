package com.ank.timer.exception;

import com.ank.timer.dto.ErrorResponse;
import com.ank.timer.dto.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerExceptionHandler {

    @ExceptionHandler(TimerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTimerNotFound(TimerNotFoundException ex) {
        ErrorRule rule = ErrorRule.TIMER_NOT_FOUND;

        return ResponseEntity
                .status(rule.getHttpStatus())
                .body(createErrorResponse(rule.name(), rule.getDescription()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        ValidationErrorResponse validationErrorVo = new ValidationErrorResponse();

        for (FieldError fieldError : fieldErrors) {
            validationErrorVo.addFieldError(fieldError.getObjectName(), fieldError.getField(),
                                            fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validationErrorVo);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex) {

        ValidationErrorResponse validationErrorVo = new ValidationErrorResponse();

        String[] exceptionMessages = ex.getMessage().split(",");

        for (String message : exceptionMessages) {
            validationErrorVo.addFieldError(message.split(":")[0], message.split(":")[0], message.split(":")[1]);
        }
        return new ResponseEntity<>(validationErrorVo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Throwable ex) {
        log.error("Internal server error: ", ex);

        ErrorRule rule = ErrorRule.TECHNICAL_ERROR;

        return ResponseEntity
                .status(rule.getHttpStatus())
                .contentType(APPLICATION_JSON)
                .body(createErrorResponse(rule.name(), ex.getMessage()));
    }

    private ErrorResponse createErrorResponse(String code, String message) {
        return ErrorResponse.builder()
                            .code(code)
                            .message(message)
                            .build();
    }

}
