package com.csci334.ConferenceMagment.domain.observer;


import org.springframework.mail.SimpleMailMessage;

public class EmailDecision implements Observer {


    private String username;
    private Long paperId;
    private String type;
    private String decisionMaker;

    private String sendTo;

    public EmailDecision(
            String username,
            Long paperId,
            String type,
            String sendTo,
            String decisionMaker
    ) {
            this.username = username;
            this.paperId = paperId;
            this.type = type;
            this.sendTo = sendTo;
            this.decisionMaker = decisionMaker;
    }

    @Override
    public SimpleMailMessage sendNotifcation(){
        return setEmail();
    }

    public SimpleMailMessage setEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        String body = "Dear " + this.username + "\nWe hope you are doing well.\n"
                + "Your paper has been decided by " + this.decisionMaker +"\n"
                + "Paper ID : " + this.paperId +"\n"
                + "To check your decision please log in to the system\n\n"
                +"\n Best Regards\n Conference Management Tool";
        String subject = this.type + " " +this.paperId;
        message.setFrom("cheerepninyuri@gmail.com");
        message.setTo(this.sendTo);
        message.setText(body);
        message.setSubject(subject);
        return message;
    }
}
