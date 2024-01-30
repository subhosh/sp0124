package com.sp.trms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RentalDataNotFoundException extends RuntimeException {
    public RentalDataNotFoundException() {
        super();
    }
    public RentalDataNotFoundException(String message) {
        super(message);
    }
    public RentalDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public RentalDataNotFoundException(Throwable cause) {
        super(cause);
    }
}
