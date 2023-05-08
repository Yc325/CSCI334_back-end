package com.csci334.ConferenceMagment.domain.builderPattern;

import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DecisionNotification implements BuildNotification {
    private Notification notification;
    private User receiver;
    private Paper paper;
    private User sender;



    public DecisionNotification(User receiver, Paper paper,User sender ) {
        this.notification = new Notification();
        this.receiver = receiver;
        this.paper  = paper;
        this.sender = sender;
    }

    @Override
    public void buildSender(){
        notification.setSender(sender);
    };
    @Override
    public void buildDate(){
        notification.setDate(LocalDate.from(LocalDateTime.now()));
    };
    @Override
    public void buildMsg(){
        notification.setMsg("Decision has been made");
    };
    @Override
    public void buildPaper(){
        notification.setPaper(paper);
    };
    @Override
    public void buildReceiver(){
        notification.setReceiver(receiver);
    };
    @Override
    public void buildType(){
        notification.setType("Conference Decision");
    };

    public Notification getNotification(){
        return notification;
    };
}
