package com.qc.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.qc.common.ErrorCode;
import com.qc.entity.User;
import com.qc.exception.BusinessException;
import com.qc.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.*;
import static com.qc.common.UserConstant.USER_LOGIN_STATE;



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


    public BigInteger editUser(BigInteger id, String userAccount,String userPassword, String avatar, String nickName, Integer gender, String email, String userIntro, Integer birthday) {
        /**
         * 当id不为null且此id可以查询到user的时候做update操作
         * 1. 更新时间，注册Ip,上次登陆时间，上次登陆Ip，是否删除 为上次的记录
         */

        User user = new User();

        if (id != null) {
            User oldUser = getById(id);
            if (oldUser == null) {
                throw new RuntimeException("This user does not exist");
            }
            mapper.update(user);
            return id;
        }

        user.setId(id);
        user.setUserAccount(userAccount);
        user.setUserPassword(userPassword);
        user.setAvatar(avatar);
        user.setNickName(nickName);
        user.setGender(gender);
        user.setEmail(email);
        user.setUserIntro(userIntro);
        user.setBirthday(birthday);
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

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "qc";

    public BigInteger userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new RuntimeException("参数为空");
        }
        if (userAccount.length() < 4) {
            throw new RuntimeException( "用户账号过短");
        }
        if (userPassword.length() < 6 || checkPassword.length() < 6) {
            throw new RuntimeException("用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            //QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("userAccount", userAccount);
            BigInteger id = mapper.extractByAccount(userAccount);
            if (id !=null) {
                throw new RuntimeException("账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 分配 accessKey, secretKey
            String accessKey = DigestUtil.md5Hex(SALT + userAccount + RandomUtil.randomNumbers(5));
            String secretKey = DigestUtil.md5Hex(SALT + userAccount + RandomUtil.randomNumbers(8));
            // 4. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setAccessKey(accessKey);
            user.setSecretKey(secretKey);
            user.setNickName(userAccount);
            Integer createTime = (int)System.currentTimeMillis()/1000;
            user.setCreateTime(createTime);
            int saveResult = mapper.insert(user);
            if (saveResult==0) {
                throw new RuntimeException( "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    public String userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new RuntimeException("参数为空");
        }
        if (userAccount.length() < 4) {
            throw new RuntimeException("用户账号过短");
        }
        if (userPassword.length() < 6 ) {
            throw new RuntimeException("用户密码过短");
        }

        synchronized (userAccount.intern()) {
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 查询用户是否存在
        User user = mapper.extractByAccountAndEncryptPassword(userAccount,encryptPassword);
        if(user==null){
            throw new RuntimeException("用户不存在或密码错误");
        }
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // 5. 返回一个hashMap或者sign
        /**
         * 1.给app分配对应的key、secret? ????咋弄
         * 2.Sign签名，调用API 时需要对请求参数进行签名验证，签名方式如下：
         * a. 按照请求参数名称将所有请求参数按照字母先后顺序排序得到：keyvaluekeyvalue...keyvalue 字符串如：
         * 将arong=1,mrong=2,crong=3 排序为：arong=1, crong=3,mrong=2 然后将参数名和参数值进行拼接得到参数字符串：arong1crong3mrong2。
         * b. 将secret加在参数字符串的头部后进行MD5加密 ,加密后的字符串需大写。即得到签名Sign
         */
        HashMap<String,String> hashMap = new HashMap<>();

        StringBuilder whole = new StringBuilder();
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        s1.append("userAccount" + userAccount);
        s2.append("userPassword" + encryptPassword);
        //先不排序了：比较char值大小，再放进一个新String里
        whole.append(s1).append(s2);
        String encyWhole = DigestUtils.md5DigestAsHex(whole.toString().getBytes());
        String sign = encyWhole.toUpperCase();

        return sign;
        }
    }


}

