package com.example.evas.Models;

public class Drivers {
    String profilePic = "https://www.pngall.com/wp-content/uploads/12/Driver-PNG-Picture.png";
    String userName;
    String mail;
    String password;
    String key;
    String status;
    String address;
    String Name;
    String phoneNumber;



    String aadharNo;

    public Drivers(){

    }

    public Drivers(String profilePic, String userName, String mail, String password, String key, String status) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.key = key;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Drivers(String Name, String phoneNumber, String address, String aadharNo, String userName, String password, String key) {
        this.Name = Name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.aadharNo = aadharNo;
        this.userName = userName;
        this.password = password;
        this.key = key;
    }

    public Drivers(String mail, String password, String key) {
        this.mail = mail;
        this.password = password;
        this.key = key;
    }

    public String getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAadharNo() {return aadharNo;}
    public void setAadharNo(String aadharNo) {this.aadharNo = aadharNo;}

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
}
