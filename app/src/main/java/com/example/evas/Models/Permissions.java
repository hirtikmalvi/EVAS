package com.example.evas.Models;

public class Permissions {

    String senderToken, receiverToken, driver, admin, message;
    Boolean isAccepted = false;

    public Permissions(String senderToken, String receiverToken, String driver, String admin, String message, Boolean isAccepted) {
        this.senderToken = senderToken;
        this.receiverToken = receiverToken;
        this.driver = driver;
        this.admin = admin;
        this.message = message;
        this.isAccepted = isAccepted;
    }

    public Permissions() {
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getReceiverToken() {
        return receiverToken;
    }

    public void setReceiverToken(String receiverToken) {
        this.receiverToken = receiverToken;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }
}
