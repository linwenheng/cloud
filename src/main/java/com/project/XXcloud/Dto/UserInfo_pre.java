package com.project.XXcloud.Dto;

import com.project.XXcloud.Mbg.Model.UserInfo;

public class UserInfo_pre {
    private int userID;
    private String email;
    private String userName;

    public UserInfo_pre(UserInfo userInfo)
    {
        this.userID = userInfo.getUserId();
        this.userName = userInfo.getUserName();
        this.email = userInfo.getEmail();
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
