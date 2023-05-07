package com.csci334.ConferenceMagment.service;

import com.csci334.ConferenceMagment.domain.Notification;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.domain.exception.NotificationNotFoundException;
import com.csci334.ConferenceMagment.domain.exception.PaperNotFoundException;
import com.csci334.ConferenceMagment.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    public Set<Notification> getAllNotificationsByuser(User user) {
        User newUser = user;
        Set<Notification> notifications = notificationRepository.findAllByUser(newUser.getId());
        return notifications;
    }

    public void setReadNotification(Long notificationId) {
        Notification notification =  notificationRepository.findById(notificationId).orElseThrow(()-> new NotificationNotFoundException(notificationId));
        notification.setStatus(true);
        notificationRepository.save(notification);



    }
}
