package com.dlsc.jfxcentral2.model;

public class RegisteredUser {
    private String userName;
    private String fullName;
    private String email;


    public RegisteredUser() {
    }

    public RegisteredUser(String userName, String fullName, String email) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
