package com.project.XXcloud.ServiceImpl;

import com.project.XXcloud.Common.JwtTokenUtil;
import com.project.XXcloud.Mbg.Mapper.UserInfoMapper;
import com.project.XXcloud.Mbg.Model.UserInfo;
import com.project.XXcloud.Mbg.Model.UserInfoExample;
import com.project.XXcloud.Service.RedisService;
import com.project.XXcloud.Service.UserInfoService;
import org.omg.CORBA.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /*
    注册用户
     */
    @Override
    public int addUserInfo(UserInfo userInfo) {
        //对密码进行加密
        String encodePasswd = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encodePasswd);
        return userInfoMapper.insert(userInfo);
    }

    /*
    *查找用户（通过邮箱，密码）
     */
    @Override
    public UserInfo selectUserInfo(UserInfo userInfo) {
        UserInfo user = null;
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.or().andEmailEqualTo(userInfo.getEmail()).andPasswordEqualTo(userInfo.getPassword());
        List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);

        if(userInfos.size() == 1) {
            user = userInfos.get(0);
            redisService.set(user.getEmail(),user);
        }
        return user;
    }

    /*
    *查找用户（通过邮箱）
     */
    @Override
    public UserInfo selectUserInfoByEmail(String email) {
        UserInfo user = null;
        user = redisService.get(email);
        if (user != null) return user;
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.or().andEmailEqualTo(email);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);

        if(userInfos.size() == 1) {
            user = userInfos.get(0);
            redisService.set(user.getEmail(),user);
        }
        return user;
    }

    @Override
    public UserInfo selectUserInfoByID(int ID) {
        return userInfoMapper.selectByPrimaryKey(ID);
    }

    /*
    更新用户信息
     */
    @Override
    public int updateUserInfo(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.or().andEmailEqualTo(userInfo.getEmail());
        int res  = userInfoMapper.updateByExample(userInfo,userInfoExample);
        redisService.remove(userInfo.getEmail());
        return res;
    }
    /*
    用户登录（使用Token,security)
    返回生成的Token
     */
    @Override
    public String logIn(UserInfo userInfo) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getEmail());
            if (!passwordEncoder.matches(userInfo.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }
}
