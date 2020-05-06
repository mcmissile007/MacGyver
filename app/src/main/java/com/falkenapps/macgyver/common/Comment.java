package com.falkenapps.macgyver.common;

import java.util.Date;

/**
 * Created by FalkenApps:falken on 6/30/17.
 */

public class Comment {
    private String text;
    private Date date;
    private String authorName;
    private String authorKey;
    private double score;

    public Comment(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorKey() {
        return authorKey;
    }

    public void setAuthorKey(String authorKey) {
        this.authorKey = authorKey;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", date=" + date +
                ", authorName='" + authorName + '\'' +
                ", authorKey='" + authorKey + '\'' +
                ", score=" + score +
                '}';
    }
}
