package com.project.XXcloud.Controller;

import com.project.XXcloud.Mbg.Model.UserInfo;
import com.project.XXcloud.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.Date;

@Controller
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;



    /*
    *检测邮箱是由已经注册：未注册返回true，注册过返回false；
     */
    @PostMapping("/user/email")
    public boolean checkEmail(UserInfo userInfo)
    {
        if (userInfoService.selectUserInfoByEmail(userInfo.getEmail()) == null)
        {
            return true;
        }
        else
            return false;
    }

    /*
     *用户注册：注册成功返回true，失败返回false；
     */
    @PostMapping("/user/register")
    public boolean userRegister(UserInfo userInfo)
    {
        if(userInfoService.addUserInfo(userInfo) == 1)
            return true;
        else
            return false;
    }

    /*
     *用户登录：邮箱密码正确返回true，失败返回false；
     */
    @PostMapping("/user/login")
    public boolean userLogin(UserInfo userInfo)
    {
        if(userInfoService.selectUserInfo(userInfo) != null)
            return true;
        else
            return false;

    }
}
