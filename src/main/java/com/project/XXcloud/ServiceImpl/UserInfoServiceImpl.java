package com.project.XXcloud.ServiceImpl;

import com.project.XXcloud.Mbg.Mapper.UserInfoMapper;
import com.project.XXcloud.Mbg.Model.UserInfo;
import com.project.XXcloud.Mbg.Model.UserInfoExample;
import com.project.XXcloud.Service.UserInfoService;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int addUserInfo(UserInfo userInfo) {

        return userInfoMapper.insert(userInfo);
    }

    /*
    *查找用户（通过邮箱，密码）
     */
    @Override
    public UserInfo selectUserInfo(UserInfo userInfo) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.or().andEmailEqualTo(userInfo.getEmail()).andPasswordEqualTo(userInfo.getPassword());
        List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
        UserInfo user = null;
        if(userInfos.size() == 1) user = userInfos.get(0);
        return user;
    }

    /*
    *查找用户（通过邮箱）
     */
    @Override
    public UserInfo selectUserInfoByEmail(String email) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.or().andEmailEqualTo(email);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
        UserInfo user = null;
        if(userInfos.size() == 1) user = userInfos.get(0);
        return user;
    }

    /*
    更新用户信息
     */
    @Override
    public int updateUserInfo(UserInfo userInfo) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.or().andEmailEqualTo(userInfo.getEmail());
        return userInfoMapper.updateByExample(userInfo,userInfoExample);
    }
}
