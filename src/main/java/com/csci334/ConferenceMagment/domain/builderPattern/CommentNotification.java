package com.csci334.ConferenceMagment.domain.builderPattern;

import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CommentNotification implements BuildNotification {
    private Notification notification;
    private User receiver;
    private Paper paper;
    private String message;
    private User sender;



    public CommentNotification(User receiver, String msg, Paper paper,User sender ) {
        this.notification = new Notification();
        this.receiver = receiver;
        this.message = msg;
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
        notification.setMsg(message);
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
        notification.setType("Comment");
    };

    public Notification getNotification(){
        return notification;
    };
}
