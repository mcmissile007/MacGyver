package com.falkenapps.macgyver.common;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by FalkenApps:falken on 4/10/17.
 */
@IgnoreExtraProperties
public class Professional {
    private String key;
    private String name;
    private String email;
    private boolean guard;
    private HashMap<String,Boolean> jobs;
    private String phone;
    private String description;
    private double score;
    private double pricePerHour;
    private double departurePrice;
    private FullGeoLocation fullGeoLocation;
    private double distance;
    private String city;
    private String town;
    private String country;
    private String region;
    private String activity;
    private String businessAddress;
    private String smallBusinessAddress;
    private String businessURL;
    private String photoURL;
    private boolean macGyver;
    private boolean allDaysOfYear;
    private String title;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getSmallBusinessAddress() {
        return smallBusinessAddress;
    }

    public void setSmallBusinessAddress(String smallBusinessAddress) {
        this.smallBusinessAddress = smallBusinessAddress;
    }

    public String getBusinessURL() {
        return businessURL;
    }

    public void setBusinessURL(String businessURL) {
        this.businessURL = businessURL;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public boolean isMacGyver() {
        return macGyver;
    }

    public void setMacGyver(boolean macGyver) {
        this.macGyver = macGyver;
    }

    public boolean isAllDaysOfYear() {
        return allDaysOfYear;
    }

    public void setAllDaysOfYear(boolean allDaysOfYear) {
        this.allDaysOfYear = allDaysOfYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Professional that = (Professional) o;

        return key.equals(that.key);

    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    public String getKey() {
        return key;

    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGuard() {
        return guard;
    }

    public void setGuard(boolean guard) {
        this.guard = guard;
    }

    public HashMap<String, Boolean> getJobs() {
        return jobs;
    }

    public void setJobs(HashMap<String, Boolean> jobs) {
        this.jobs = jobs;
    }

    public void updateJobs(String key, Boolean value){
        this.jobs.put(key,value);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public double getDeparturePrice() {
        return departurePrice;
    }

    public void setDeparturePrice(double departurePrice) {
        this.departurePrice = departurePrice;
    }

    public FullGeoLocation getFullGeoLocation() {
        return fullGeoLocation;
    }

    public void setFullGeoLocation(FullGeoLocation fullGeoLocation) {
        this.fullGeoLocation = fullGeoLocation;
    }

    public Professional(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public Professional(String name, String email){
        this.email = email;
        this.name = name;
        this.jobs = new HashMap<>();

    }

    @Override
    public String toString() {
        return "Professional{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", guard=" + guard +
                ", jobs=" + jobs +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", score=" + score +
                ", pricePerHour=" + pricePerHour +
                ", departurePrice=" + departurePrice +
                ", fullGeoLocation=" + fullGeoLocation +
                ", distance=" + distance +
                ", city='" + city + '\'' +
                ", town='" + town + '\'' +
                ", country='" + country + '\'' +
                ", activity='" + activity + '\'' +
                ", businessAddress='" + businessAddress + '\'' +
                ", smallBusinessAddress='" + smallBusinessAddress + '\'' +
                ", businessURL='" + businessURL + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", macGyver=" + macGyver +
                ", allDaysOfYear=" + allDaysOfYear +
                ", title='" + title + '\'' +
                '}';
    }
}
