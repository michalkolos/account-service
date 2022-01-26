package com.michalkolos.account.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unspecified server exception.")
public class UnspecifiedServerException extends RuntimeException {
}
