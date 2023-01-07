package com.exception;

import static com.constant.ErrorCodes.USER_CONTACT_NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserContactNotFoundException extends BaseMyException {

    public UserContactNotFoundException() {
        super(USER_CONTACT_NOT_FOUND, BAD_REQUEST, "User Contact Not Found");
    }
}
