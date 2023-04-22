package com.csci334.ConferenceMagment.domain;

import javax.persistence.*;

@Entity
@Table(name="score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;
    @ManyToOne(optional = false)
    private User reviwer;
    @ManyToOne(optional = false)
    private Paper paper;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User getReviwer() {
        return reviwer;
    }

    public void setReviwer(User reviwer) {
        this.reviwer = reviwer;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}
