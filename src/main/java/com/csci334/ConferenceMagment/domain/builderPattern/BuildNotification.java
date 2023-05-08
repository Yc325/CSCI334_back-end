package com.csci334.ConferenceMagment.domain.builderPattern;

public interface BuildNotification {
    void buildSender();
    void buildDate();
    void buildMsg();
    void buildPaper();
    void buildReceiver();
    void buildType();
    Notification getNotification();


}
