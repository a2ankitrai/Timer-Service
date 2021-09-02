package com.ank.timer.exception;

public class TimerNotFoundException extends RuntimeException {

    public TimerNotFoundException(final String message, final Throwable t) {
        super(message, t);
    }

    public TimerNotFoundException() {
    }


}


