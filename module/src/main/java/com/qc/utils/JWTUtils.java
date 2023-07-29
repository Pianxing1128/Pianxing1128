package com.qc.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
@Slf4j
public class JWTUtils {

    private final static String signature = "qc!@#$%^";

    //生成token ： header.payload.signature
    public static String getToke(Map<String, String> map){
        //7天过期
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);
        //创建jwt Builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(instance.getTime())
                        .sign(Algorithm.HMAC256(signature));
        return token;
    }

    //验证token合法性
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
    }

    //获取token信息
    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(signature)).build().verify(token);
        return verify;
    }
    //______________________________________________________//


    @Value("${my.secretKey}")
    private String secret; //密钥

    public String createJwt(String userInfo, List<String> authList) {
        Date issDate = new Date(); //签发时间时间
//        Date expireDate = new Date(issDate.getTime() + 1000 *30); //过期时间30秒
        Date expireDate = new Date(issDate.getTime() + 1000 * 60 * 60 * 2); //当前时间加上两个小时
        //头部
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
        return JWT.create().withHeader(headerClaims)
                .withIssuer("thomas") //设置签发人
                .withIssuedAt(issDate) //签发时间
                .withExpiresAt(expireDate)
                .withClaim("user_info", userInfo) //自定义声明 放入登录用户信息
                .withClaim("userAuth", authList)//自定义声明
                .sign(Algorithm.HMAC256(secret)); //使用HS256进行签名，使用secret作为密钥
    }

    public boolean verifyToken(String jwtToken){
        //创建校验器
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            //校验token
            DecodedJWT decodedJwt = jwtVerifier.verify(jwtToken);
            log.info("token验证正确");
            return true;
        } catch (Exception e) {
            log.error("token验证不正确！！！");
            return false;
        }
    }

    /**
     * 从jwt的payload里获取声明，获取的用户的信息
     * @param jwt
     * @return
     */
    public String getUserInfoFromToken(String jwt){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            return decodedJWT.getClaim("user_info").asString();
        } catch (IllegalArgumentException e) {
            return "";
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    /**
     * 从jwt的payload里获取声明，获取的用户的权限
     * @param jwt
     * @return
     */
    public List<String> getUserAuthFromToken(String jwt){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            return decodedJWT.getClaim("userAuth").asList(String.class);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (JWTVerificationException e) {
            return null;
        }
    }


}
