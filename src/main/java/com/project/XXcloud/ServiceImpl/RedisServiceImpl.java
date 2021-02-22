package com.project.XXcloud.ServiceImpl;

import com.project.XXcloud.Mbg.Model.UserInfo;
import com.project.XXcloud.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void set(String email, UserInfo userInfo) {

        //stringRedisTemplate.opsForValue().set(key,value);
        redisTemplate.opsForValue().set("email:" + email,userInfo);
    }

    @Override
    public boolean hasKey(String email) {
        return redisTemplate.hasKey("email:" + email);
    }

    @Override
    public UserInfo get(String email) {
        return (UserInfo)redisTemplate.opsForValue().get("email:" + email);
    }

    @Override
    public boolean expire(String email, long expire) {
        return redisTemplate.expire("email:" + email,expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String email) {
        redisTemplate.delete("email:" + email);
    }

}
