package com.csci334.ConferenceMagment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="paper")
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @OneToMany(cascade=CascadeType.ALL)
//    @JsonIgnore
//    private List<Score> scores = new ArrayList<>();
    private String status;
    private Boolean ConferenceManagementDecision = null;


    @OneToMany(cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @ManyToMany(cascade=CascadeType.ALL)
    private List<User> authors = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL)
    private List<User> reviewers = new ArrayList<>();

    @ManyToOne(cascade=CascadeType.ALL)
    private File file;


    public Boolean getConferenceManagementDecision() {
        return ConferenceManagementDecision;
    }

    public void setConferenceManagementDecision(Boolean conferenceManagementDecision) {
        ConferenceManagementDecision = conferenceManagementDecision;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<User> reviewer) {
        this.reviewers = reviewer;
    }

    public void addAuthor(User user){
        authors.add(user);
    }

    public void addReviewer(User user){
        reviewers.add(user);
    }

//    public void addScore(Score paperscore){
//        scores.add(paperscore);
//    }


    public void addComment(Comment comment){
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getAuthors() {
        return authors;
    }

    public void setAuthors(List<User> authors) {
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Score> getScore() {
//        return scores;
//    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
