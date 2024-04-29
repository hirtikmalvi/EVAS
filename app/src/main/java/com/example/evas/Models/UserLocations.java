package com.example.evas.Models;

import com.google.firebase.database.PropertyName;

public class UserLocations {

    String latitude;
    String longitude;

    String userToken;

    public UserLocations(String latitude, String longitude, String userToken) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userToken = userToken;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }


}
