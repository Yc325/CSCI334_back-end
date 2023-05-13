package com.csci334.ConferenceMagment.domain.observer;

import org.springframework.mail.SimpleMailMessage;

public interface Observer {
    SimpleMailMessage sendNotifcation();
}
