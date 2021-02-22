package com.project.XXcloud.Service;

import com.project.XXcloud.Mbg.Model.UserInfo;

/*
*Redis服务接口
 */
public interface RedisService {
    /**
     * 存储用户信息
     */
    void set(String email, UserInfo userInfo);
    /*
    *是否含有key
     */
    boolean hasKey(String email);
    /**
     * 获取数据
     */
    UserInfo get(String email);

    /**
     * 设置超期时间
     */
    boolean expire(String userId, long expire);

    /**
     * 删除数据
     */
    void remove(String userId);


}
