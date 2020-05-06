package com.falkenapps.macgyver.common;

import java.util.Date;
import java.util.List;

/**
 * Created by FalkenApps:falken on 4/24/17.
 */

public class Search {
    private String job;
    private List<String> tags;
    private boolean guard;
    private FullGeoLocation fullGeoLocation;
    private int radius;
    private String userEmail;
    private Date date;

    public FullGeoLocation getFullGeoLocation() {
        return fullGeoLocation;
    }

    public void setFullGeoLocation(FullGeoLocation fullGeoLocation) {
        this.fullGeoLocation = fullGeoLocation;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Search(String job){
        this.job = job;
        this.radius = 20;

    }

    public String getJob() {
        return job.toLowerCase();
    }

    public void setJob(String job) {
        this.job = job.toLowerCase();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag){
        this.tags.add(tag);
    }

    public void removeTag(String tag){
        this.tags.remove(tag);
    }

    public boolean isGuard() {
        return guard;
    }

    public void setGuard(boolean guard) {
        this.guard = guard;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
