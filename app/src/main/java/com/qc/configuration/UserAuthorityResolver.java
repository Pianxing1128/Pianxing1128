package com.qc.configuration;

import com.alibaba.fastjson.JSON;
import com.qc.annotations.VerifiedUser;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.BaseUtils;
import com.qc.utils.SignUtils;
import com.qc.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@Slf4j
public class UserAuthorityResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;
    private boolean isCheckAuthority;

    public UserAuthorityResolver(ApplicationArguments appArguments) {
        String[] arguments = appArguments.getSourceArgs();
        if (arguments == null || arguments.length <= 3) {
            isCheckAuthority = true;
            return ;
        }

        String isMockUserLogin = arguments[2];
        if (BaseUtils.isEmpty(isMockUserLogin)) {
            isCheckAuthority = true;
        } else {
            isCheckAuthority = Boolean.parseBoolean(isMockUserLogin);
        }
        log.info("Check user authority: {}", Boolean.toString(isCheckAuthority));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> type = parameter.getParameterType();
        return type.isAssignableFrom(User.class) && parameter.hasParameterAnnotation(VerifiedUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest webRequest, WebDataBinderFactory factory) {

        if (isCheckAuthority) {
            String isAppS = SpringUtils.getProperty("application.isapp");
            boolean isApp = isAppS.equals("1") ? true : false;
            HttpServletRequest sRequest = (HttpServletRequest)webRequest.getNativeRequest();
            if(isApp){
                String signKey = SpringUtils.getProperty("application.sign.key");
                String sign = sRequest.getHeader(signKey);
                sign = "eyJleHBpcmF0aW9uIjoxNjgyNTg1MDI2LCJzYWx0IjoicWMiLCJ1c2VySWQiOjF9";
                if(!BaseUtils.isEmpty(sign)){
                    BigInteger userId = SignUtils.parseSign(sign);
                    log.info("userId: {}, sign: {}", userId, sign);
                    if (!BaseUtils.isEmpty(userId)) {
                        return userService.getById(userId);
                    }
                }
                return null;
            }else{
                HttpSession session = sRequest.getSession(false);
                if(BaseUtils.isEmpty(session)){
                    return null;
                }
                String signKey = SpringUtils.getProperty("application.session.key");
                Object value = session.getAttribute(signKey);
                if (value == null) {
                    return null;
                }

                String sValue = (String)value;
                return JSON.parseObject(sValue, User.class);
            }
        }

        return userService.getById(BigInteger.valueOf(1));
    }
}
