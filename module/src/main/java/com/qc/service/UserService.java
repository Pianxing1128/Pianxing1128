package com.qc.service;

import com.qc.entity.Teacher;
import com.qc.entity.User;
import com.qc.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qc112
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private UserMapper mapper;

    public int insert(User user){
        return mapper.insert(user);
    }

    public int update(User user){
        return mapper.update(user);
    }

    public User getById(BigInteger id){
        return mapper.getById(id);
    }

    public User extractById(BigInteger id){
        return mapper.extractById(id);
    }

    public int delete(BigInteger id){
        User oldUser = mapper.getById(id);
        if(oldUser==null){
            throw new RuntimeException("This user does not exist");
        }
        Integer updateTime = (int)System.currentTimeMillis()/1000;
        return mapper.delete(id,updateTime);
    }

    public Integer getTotal(){
        return mapper.getTotal();
    }

    public List<User> getUsersForApp(Integer pageNum,Integer pageSize){
        int begin = (pageNum -1)*pageSize;
        return mapper.getUsersForApp(begin,pageSize);
    }

    public List<User> getUsersForConsole(Integer pageNum,Integer pageSize){
        int begin = pageNum-1;
        return mapper.getUsersForConsole(begin,pageSize);
    }


    public BigInteger editUser(BigInteger id, String avatar, String nickName, Integer gender, String email, String userIntro, Integer birthday) {
        /**
         * 当id不为null且此id可以查询到user的时候做update操作
         * 1. 更新时间，注册Ip,上次登陆时间，上次登陆Ip，是否删除 为上次的记录
         */
        User user = new User();
        user.setId(id);
        user.setAvatar(avatar);
        user.setNickName(nickName);
        user.setGender(gender);
        user.setEmail(email);
        user.setUserIntro(userIntro);
        user.setBirthday(birthday);

        if (id != null) {
            User oldUser = getById(id);
            if (oldUser == null) {
                throw new RuntimeException("This user does not exist");
            }
            mapper.update(user);
            return id;
        }
        /**
         * id为null的时候使用 insert 操作
         * 注册时间，注册Ip,上次登陆时间，上次登陆Ip为本次的记录
         * 无更新时间，是否删除默认为0
         */
        user.setLastLoginIp(user.getLastLoginIp());
        user.setRegisterIp(user.getRegisterIp());
        user.setLastLoginTime(user.getLastLoginTime());
        int now = (int)System.currentTimeMillis()/1000;
        user.setCreateTime(now);
        user.setIsDeleted(0);
        mapper.insert(user);
        return user.getId();

    }
    public String getUserIdsByNickName(String nickName) {

        if (nickName == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        List<BigInteger> userIdsByNickName = mapper.getUserIdsByNickname(nickName);
        for (BigInteger x : userIdsByNickName) {
            sb.append(x + ",");
        }
        int len = sb.length();

        if (len == 0) {
            return "-1";
        }
        sb.delete(len - 1, len);
        String UserIdsForTeacher = sb.toString();
        return UserIdsForTeacher;
    }

    public List<User> getByIds(List<BigInteger> userIds){
        StringBuilder ss = new StringBuilder();
        for(BigInteger x:userIds){
            ss.append(x+",");
        }
        int len = ss.length();
        if(len==0){
            return new ArrayList<>();
        }
        ss.delete(len-1,len);
        String UserIdsFromTeacher = ss.toString();
        return mapper.getByIds(UserIdsFromTeacher);
    }



}

