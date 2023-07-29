package com.qc.module.user.service;

import com.qc.module.user.entity.MySecurityUser;
import com.qc.module.user.entity.User;
import com.qc.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;
    @Resource
    private UserMenuService userMenuService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getUser(userName);
        if(BaseUtils.isEmpty(user)){
            throw new UsernameNotFoundException("This user does not exist!");
        }
        //根据用户id获取该用户所拥有的权限，List<SimpleGrantedAuthority>
        List<String> userPermissions = userMenuService.getPermissionsByUserId(user.getId());
        //遍历权限，把权限放到
        List<SimpleGrantedAuthority> authorityList = userPermissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String password = bCryptPasswordEncoder.encode("123456");
//        log.info(password);

        MySecurityUser securityUser=new MySecurityUser(user);
        securityUser.setAuthorityList(authorityList);
        return securityUser;
    }
}
