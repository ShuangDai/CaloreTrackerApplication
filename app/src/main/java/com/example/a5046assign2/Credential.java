package com.example.a5046assign2;

import java.util.Date;

public class Credential {
    private String credentialId;
    private String userName;
    private String password;
    private String signUpDate;
    private UserInfo userId;

    public Credential(String credentialId, String userName, String password, String signUpDate, UserInfo userId) {
        this.credentialId = credentialId;
        this.userName = userName;
        this.password = password;
        this.signUpDate = signUpDate;
        this.userId=userId;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }

    public UserInfo getUserInfo() {
        return userId;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userId = userInfo;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "credentialId='" + credentialId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", signUpDate=" + signUpDate +
                ", userId=" + userId +
                '}';
    }
}
