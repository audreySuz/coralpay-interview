package com.example.dynamobankservice.exceptions;

import com.example.dynamobankservice.domains.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ValidatorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse handleValidatorException(final ValidatorException ex) {
        log.error("Validation error: {} ", ex.getMessage());
        return new AppResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                ex.getMessage(), null, ex.getMessage());
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public AppResponse notFoundException(final NotFoundException ex) {
        log.error("Not found error: {} ", ex.getMessage());
        return new AppResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                ex.getMessage(), null, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public AppResponse authenticationException(final AuthenticationException ex) {
        log.error("Unauthorized error: {} ", ex.getMessage());
        return new AppResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(),
                ex.getMessage(), null, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse illegalArgumentException(final IllegalArgumentException ex) {
        log.error("illegal argument error: {} ", ex.getMessage());
        return new AppResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                ex.getMessage(), null, ex.getMessage());
    }
}
