package com.project.XXcloud.Dto;


import com.project.XXcloud.Mbg.Model.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {

    UserInfo userInfo;


    public UserInfoDetails(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        GrantedAuthority au = new SimpleGrantedAuthority("ROLE_USER");
        list.add(au);
        return list;

    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }
    /*
    *使用email代替用户名
     */
    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
