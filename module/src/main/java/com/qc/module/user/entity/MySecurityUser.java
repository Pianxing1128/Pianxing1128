package com.qc.module.user.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MySecurityUser implements UserDetails {
    private final User user;
    //用于存储权限的list
    private List<SimpleGrantedAuthority> authorityList;

    public MySecurityUser(User user){
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    /**
     * 返回用户所拥有的权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    public void setAuthorityList(List<SimpleGrantedAuthority> authorityList) {
        this.authorityList = authorityList;
    }

    @Override
    public String getPassword() {
        String myPassword=user.getUserPassword();
        user.setUserPassword(null); //擦除我们的密码，防止传到前端
        return myPassword;
    }

    @Override
    public String getUsername() {
        return this.user.getUserAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNoExpired().equals(1);
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNoLocked().equals(1);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNoExpired().equals(1);
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled().equals(1);
    }
}
