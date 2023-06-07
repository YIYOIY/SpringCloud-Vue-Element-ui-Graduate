package com.entity;

import lombok.ToString;

@ToString
public class Admin {
    private int adminId;
    private String adminName;
    private String adminPassword;
    private String adminPetName;
    public Admin() {
    }

    public Admin(int adminId, String adminName, String adminPassword, String adminPetName) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.adminPetName = adminPetName;
    }

    public Admin(String adminName, String adminPassword, String adminPetName) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.adminPetName = adminPetName;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPetName() {
        return adminPetName;
    }

    public void setAdminPetName(String adminPetName) {
        this.adminPetName = adminPetName;
    }
}
