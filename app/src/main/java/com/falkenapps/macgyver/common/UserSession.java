package com.falkenapps.macgyver.common;

import java.util.Date;

/**
 * Created by FalkenApps:falken on 5/30/17.
 */

public class UserSession {
    private String key;
    private String userEmail;
    private Date startDate;
    private Date endDate;
    private long duration;

    public UserSession(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public UserSession(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "key='" + key + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                '}';
    }
}
