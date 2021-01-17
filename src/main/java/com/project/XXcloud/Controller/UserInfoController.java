package com.project.XXcloud.Controller;

import com.project.XXcloud.Mbg.Model.UserInfo;
import com.project.XXcloud.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    //检测邮箱是由已经注册：未注册返回true，注册过返回false；
    @PostMapping("/user/email")
    public boolean checkEmail(String email)
    {
        if (userInfoService.selectUserInfoByEmail(email) == null)
        {
            return true;
        }
        else
            return false;
    }
    //用户注册功能
    @PostMapping("/user/register")
    public String userRegister(UserInfo userInfo)
    {
        userInfoService.addUserInfo(userInfo);
        return "login";
    }

    //用户登录
    @PostMapping("/user/login")
    public String userLogin(UserInfo userInfo)
    {
        userInfoService.selectUserInfo(userInfo);
        return "login";
    }
}
