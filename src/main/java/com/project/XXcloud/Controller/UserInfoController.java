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
import java.util.Random;

@Controller
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MailServiceImpl mailService;


//    @GetMapping("/test")
//    public String test()
//    {
//        String to = "1972073918@qq.com";
//        String subject = "验证";
//        String content = "1234";
//        try {
//            mailService.sendSimpleMail(to, subject, content);
//            System.out.println("成功了");
//
//        } catch (MailException e) {
//            System.out.println("失败了");
//            e.printStackTrace();
//        }
//        return "login";
//    }

    /*
    *检测邮箱是由已经注册：未注册返回0，注册过返回用户Id；
    * 参数为邮箱地址
     */
    @GetMapping("/user/emailIsNoRegistered")
    @ResponseBody
    public int emailIsNoRegistered(String email)
    {
        UserInfo userInfo = userInfoService.selectUserInfoByEmail(email);
        if (userInfo == null)
        {
            return 0;
        }
        else
            return userInfo.getUserId();
    }
    /*
    *邮箱验证
    * 参数为要验证的邮箱
    * 返回值为发送给用户邮箱的验证码(4位数字的字符串）
     */
    @GetMapping("/user/emailCheck")
    @ResponseBody
    public String emailCheck(String email)
    {
        String to = email;
        String subject = "验证";
        Random ran = new Random();
        int i = ran.nextInt(9000) + 1000;
        String content = String.valueOf(i);
        try {
            mailService.sendSimpleMail(to, subject, content);
        } catch (MailException e) {
            e.printStackTrace();
        }
        return content;
    }
    /*
     *用户注册：注册成功返回true，失败返回false；
     */
    @PostMapping("/user/register")
    @ResponseBody
    public boolean userRegister(UserInfo userInfo)
    {
        if(userInfoService.addUserInfo(userInfo) == 1)
            return true;
        else
            return false;
    }

    /*
     *用户登录：邮箱密码正确返回1，失败返回0或-1；
     */
    @GetMapping("/user/login")
    @ResponseBody
    public int userLogin(UserInfo userInfo)
    {
        UserInfo userInfo1 = userInfoService.selectUserInfo(userInfo);
        if(userInfo1 != null) {
            //设置session，用于填写登录日志
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("userInfo",userInfo1);
            return userInfo1.getUserId();
        }
        //邮箱未注册返回-1
        else if(userInfoService.selectUserInfoByEmail(userInfo.getEmail()) == null){
            return -1;
        }
        //密码错误返回0
        else
            return 0;
    }

    /*
    *密码修改
     */
    @PostMapping("/user/userInfoChange")
    @ResponseBody
    public boolean userInfoChange(UserInfo userInfo)
    {
        UserInfo user = userInfoService.selectUserInfoByEmail(userInfo.getEmail());
        if (user == null) return false;
        userInfoService.updateUserInfo(user);
        return true;
    }
}
