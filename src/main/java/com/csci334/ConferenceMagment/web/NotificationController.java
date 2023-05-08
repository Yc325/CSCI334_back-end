package com.csci334.ConferenceMagment.web;

import com.csci334.ConferenceMagment.domain.builderPattern.Notification;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<Set<Notification>> getAllNotifications(@AuthenticationPrincipal User user){
        Set<Notification> notifications = notificationService.getAllNotificationsByuser(user);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("")
    public ResponseEntity<?> SetReadNotification(@RequestParam Long notificationId){
        notificationService.setReadNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
