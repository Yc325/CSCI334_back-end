package com.csci334.ConferenceMagment.domain.exception;

import java.text.MessageFormat;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(Long id){
        super(MessageFormat.format("notification not found by notification ID {0}",id));
    }
}
