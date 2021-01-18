package com.project.XXcloud.Component;

import com.project.XXcloud.Service.UserLogService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
*登录日志处理切面
* 2021-1-18 林文恒 add
 */
@Aspect
@Component
public class LoginLogAspect {

    @Autowired
    private UserLogService userLogService;


}
