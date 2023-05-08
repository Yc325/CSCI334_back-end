package com.csci334.ConferenceMagment.domain.builderPattern;

public class ConstractorNotification {
    private BuildNotification buildNotification;

    public ConstractorNotification(BuildNotification buildComment) {
        this.buildNotification = buildComment;
    }
    public Notification constructNotification(){
        this.buildNotification.buildDate();
        this.buildNotification.buildMsg();
        this.buildNotification.buildPaper();
        this.buildNotification.buildReceiver();
        this.buildNotification.buildSender();
        this.buildNotification.buildType();
        return this.buildNotification.getNotification();
    }
}
