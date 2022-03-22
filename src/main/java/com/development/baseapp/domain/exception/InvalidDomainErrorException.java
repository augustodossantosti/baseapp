package com.development.baseapp.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @see com.development.baseapp.domain.exception.AbstractWebAppException
 */
@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED, reason = "Invalid Domain Error")
public class InvalidDomainErrorException extends AbstractWebAppException {

}
