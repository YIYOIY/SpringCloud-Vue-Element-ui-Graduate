package com.entity;

import lombok.ToString;

@ToString
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private String userPetName;
    private String userAddress;
    private String userTel;

    public User() {
    }

    public User(String userName, String userPassword, String userPetName, String userAddress, String userTel) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPetName = userPetName;
        this.userAddress = userAddress;
        this.userTel = userTel;
    }

    public User(int userId, String userName, String userPassword, String userPetName, String userAddress, String userTel) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPetName = userPetName;
        this.userAddress = userAddress;
        this.userTel = userTel;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPetName() {
        return userPetName;
    }

    public void setUserPetName(String userPetName) {
        this.userPetName = userPetName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}
