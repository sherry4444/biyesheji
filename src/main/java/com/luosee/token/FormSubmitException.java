package com.luosee.token;

/**
 * Created by server1 on 2017/1/11.
 */
public class FormSubmitException extends Exception {
    private static final long serialVersionUID = -4584156464145497938L;

    public FormSubmitException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormSubmitException(String message) {
        super(message);
    }
}
