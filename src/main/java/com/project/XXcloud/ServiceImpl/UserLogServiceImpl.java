package com.project.XXcloud.ServiceImpl;

import com.project.XXcloud.Mbg.Mapper.LoginLogMapper;
import com.project.XXcloud.Mbg.Model.LoginLog;
import com.project.XXcloud.Service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
*提供与登录日志相关的服务
* 2021-1-18 林文恒 change
 */
@Service
public class UserLogServiceImpl implements UserLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public int addLoginLog(LoginLog loginLog)
    {
        return loginLogMapper.insert(loginLog);
    }
}
