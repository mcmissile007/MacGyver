package com.falkenapps.macgyver.common;

/**
 * Created by FalkenApps:falken on 4/20/17.
 */

public class FullGeoLocation {
    private String cp;
    private String address;
    private double latitude;
    private double longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "FullGeoLocation{" +
                "cp='" + cp + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public FullGeoLocation(String cp, double latitude, double longitude){
        this.cp = cp;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public FullGeoLocation(){

    }
}
