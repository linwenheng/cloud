package com.project.XXcloud.Service;

import com.project.XXcloud.Mbg.Model.UserInfo;

import java.util.List;

public interface UserInfoService {
    public int addUserInfo(UserInfo userInfo);
    public UserInfo selectUserInfo(UserInfo userInfo);
    public UserInfo selectUserInfoByEmail(String email);
    public int updateUserInfo(UserInfo userInfo);
}
