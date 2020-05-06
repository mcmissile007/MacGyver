package com.falkenapps.macgyver.common;

/**
 * Created by FalkenApps:falken on 4/5/17.
 */

public class User {
    private String email;
    private String photoURL;
    private String name ;
    private boolean isSigned;

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }



    private User(){
        this.setEmail("");
        this.setSigned(false);
        this.setPhotoURL("");
        this.setName("");

    }

    private static class UserHelper{
        private static final User INSTANCE = new User();

    }

    public static User getInstance(){
        return UserHelper.INSTANCE;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
