package com.csci334.ConferenceMagment.dto;

public class CommentDto {
    private Long paperId;
    private String text;
    private String user;


    public CommentDto(Long paperId, String text, String user) {
        this.paperId = paperId;
        this.text = text;
        this.user = user;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getComment() {
        return text;
    }

    public void setComment(String comment) {
        this.text = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "paperId=" + paperId +
                ", text='" + text + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
