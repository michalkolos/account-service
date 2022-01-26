package com.michalkolos.account.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Illegal request body.")
public class IllegalRequestBodyException extends RuntimeException {
}
