package com.csci334.ConferenceMagment.repository;

import com.csci334.ConferenceMagment.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("select a from Notification a where a.receiver.id = :id and a.status=false")
    Set<Notification> findAllByUser(Long id);
}
