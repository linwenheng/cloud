package com.project.XXcloud.Component;

import com.project.XXcloud.Mbg.Model.LoginLog;
import com.project.XXcloud.Mbg.Model.UserInfo;
import com.project.XXcloud.Service.UserInfoService;
import com.project.XXcloud.Service.UserLogService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/*
*登录日志处理切面
* 2021-1-18 林文恒 add
 */
@Aspect
@Component
public class LoginLogAspect {

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserInfoService userInfoService;

    @Pointcut("execution(public * com.project.XXcloud.Controller.UserInfoController.userLogin(*))")
    public void weblog()
    {

    }

    @AfterReturning(pointcut = "weblog()",returning = "ret")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable{
        //登录失败不记录
        if(ret.equals(false)) return;
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        //记录请求信息
        LoginLog loginLog = new LoginLog();


        UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
        loginLog.setUserId(userInfoService.selectUserInfo(userInfo).getUserId());
        loginLog.setIpAddress(request.getRemoteAddr());
        loginLog.setCreateDate(new Date());
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        loginLog.setLoginBrowser(browser.getName());
        loginLog.setLoginOs(operatingSystem.getName());
        loginLog.setLoginTime(new Date());
        userLogService.addLoginLog(loginLog);

    }


}
