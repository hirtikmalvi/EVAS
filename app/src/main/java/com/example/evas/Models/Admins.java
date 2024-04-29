package com.example.evas.Models;

public class Admins {
    String profilePic;
    String userName;
    String mail;
    String password;
    String key;
    String status;



    String adminToken;

    public Admins(){

    }

    public Admins(String profilePic, String userName, String mail, String password, String key, String status) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.key = key;
        this.status = status;
    }

    public Admins(String mail, String password, String key) {
        this.mail = mail;
        this.password = password;
        this.key = key;
    }

    public Admins(String mail, String password, String key, String adminToken) {
        this.mail = mail;
        this.password = password;
        this.key = key;
        this.adminToken = adminToken;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
    }
}
