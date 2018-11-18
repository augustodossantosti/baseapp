package com.development.baseapp.restapi.errorhandling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * The class <code>{@link WebAppErrorResponse}</code> is a wrapper for any error
 * occurred during application execution.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public final class WebAppErrorResponse {

    private final String message;
    private final String url;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
