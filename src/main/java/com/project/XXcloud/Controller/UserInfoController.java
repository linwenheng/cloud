package com.project.XXcloud.Controller;

import com.project.XXcloud.Email.MailServiceImpl;
import com.project.XXcloud.Mbg.Model.UserInfo;
import com.project.XXcloud.Service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MailServiceImpl mailService;

    @GetMapping("/test")
    public String test()
    {
        String to = "1972073918@qq.com";
        String subject = "验证";
        String content = "1234";
        try {
            mailService.sendSimpleMail(to, subject, content);
            System.out.println("成功了");

        } catch (MailException e) {
            System.out.println("失败了");
            e.printStackTrace();
        }
        return "login";
    }

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
        if(userInfoService.selectUserInfo(userInfo) != null) {
            //设置session，用于填写登录日志
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("userInfo",userInfo);
            return true;
        }
        else
            return false;

    }
}
