package com.development.baseapp.restapi.errorhandling;

import com.development.baseapp.domain.exception.AbstractWebAppException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * The class <code>{@link WebAppErrorHandler}</code> is the <i>Exception Handler</i> for
 * all exceptions occurred during application execution.
 *
 * @author Augusto Santos
 * @version 1.0
 *
 * @see ControllerAdvice
 * @see ExceptionHandler
 */
@Log4j2
@ControllerAdvice
public class WebAppErrorHandler {

    /**
     * Process a specific exception and return information about it.
     *
     * @param request The original request where error happened.
     * @param exception The related exception.
     * @return Information about error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebAppErrorResponse> handleException(final HttpServletRequest request, final Exception exception) {

        String message;
        WebAppErrorResponse errorResponse;
        String uri = request.getRequestURI();

        if (exception instanceof AbstractWebAppException) {
            final ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
            message = responseStatus.reason();
            errorResponse = new WebAppErrorResponse(message, uri);
            log.error("Failed a domain operation");
            log.error("Cause of error: " + message);
            log.error("URI: " + uri);
            return ResponseEntity.status(responseStatus.value()).body(errorResponse);
        }

        message = exception.getMessage() != null ? exception.getMessage() : exception.toString();
        errorResponse = new WebAppErrorResponse(message, uri);
        log.error("Internal application error.");
        log.error("Cause of error: " + message);
        log.error("URI: " + uri);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
