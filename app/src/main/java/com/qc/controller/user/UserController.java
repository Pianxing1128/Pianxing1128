package com.qc.controller.user;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.user.UserInfoVo;
import com.qc.domain.user.UserLoginInfoVo;
import com.qc.module.course.entity.Course;
import com.qc.module.course.service.CourseService;
import com.qc.module.pointUser.service.PointUserService;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.BasePointService;
import com.qc.module.user.service.BaseUserPurchasedCourseService;
import com.qc.module.user.service.BaseUserWatchService;
import com.qc.module.user.service.UserService;
import com.qc.module.userMembership.service.UserMemberShipService;
import com.qc.module.userShareType.entity.UserShareType;
import com.qc.module.userShareType.service.UserShareTypeService;
import com.qc.utils.BaseUtils;
import com.qc.utils.IpUtils;
import com.qc.utils.Response;
import com.qc.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PointUserService pointUserService;

    @Autowired
    private UserMemberShipService userMemberShipService;

    @Autowired
    private UserShareTypeService userShareTypeService;

    @Autowired
    private BasePointService basePointService;

    @Autowired
    private BaseUserWatchService baseUserWatchService;

    @Autowired
    private BaseUserPurchasedCourseService baseUserPurchasedCourseService;

    @RequestMapping("/user/register")
    public Response userRegister(@VerifiedUser User loginUser,
                                 @RequestParam(name="userAccount")String userAccount,
                                 @RequestParam(name="userPassword") String userPassword,
                                 @RequestParam(name="checkPassword") String checkPassword,
                                 @RequestParam(name="nickName") String nickName,
                                 @RequestParam(name="gender") Integer gender,
                                 @RequestParam(name="birthday") Integer birthday,
                                 @RequestParam(name="email") String email
                                ) {

        if(!BaseUtils.isEmpty(loginUser)){
            return new Response(4004);
        }
        if(!BaseUtils.isEmpty(email)){
            email=email.trim();
        }
        //判断用户是否已经注册
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        User oldUserByUserAccount = userService.extractByUserAccount(userAccount);
        User oldUserByEmail = userService.extractByEmail(email);
        BigInteger id;
        Integer userCurrentPoint;
        if(!BaseUtils.isEmpty(oldUserByUserAccount) || !BaseUtils.isEmpty(oldUserByEmail)){
            if(oldUserByUserAccount.getIsDeleted() ==1 || oldUserByUserAccount.getIsBan() == 1){
                return new Response(1010);
            }else {
                return new Response(1011);
            }
        }else{
            try {
                  id = userService.userRegister(userAccount,userPassword,checkPassword,gender,nickName,birthday,email,IpUtils.getIpAddress(request));
                  userCurrentPoint = basePointService.registerForGiftPoint(id);
            }catch (Exception e){
                return new Response(4004);
            }
        }

        UserInfoVo userInfo = new UserInfoVo();
        User user = userService.extractById(id);
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setUserAccount(user.getUserAccount());
        userInfo.setNickName(user.getNickName());
        userInfo.setEmail(user.getEmail());
        userInfo.setBirthday(BaseUtils.timeStamp2Date(user.getBirthday()));
        userInfo.setGender(user.getGender());
        userInfo.setUserIntro(user.getUserIntro());
        userInfo.setUserCurrentPoint(userCurrentPoint);
        UserLoginInfoVo loginInfo = new UserLoginInfoVo();
        loginInfo.setSign(SignUtils.makeSign(user.getId()));
        loginInfo.setUserInfo(userInfo);

        return new Response(1001, loginInfo);

    }

    @RequestMapping("/user/login")
    public Response userLogin(@VerifiedUser User loginUser,
                              @RequestParam(name="userAccount")String userAccount,
                              @RequestParam(name="userPassword") String userPassword) {

        if(!BaseUtils.isEmpty(loginUser)){
            return new Response(4004);
        }

        boolean result = userService.login(userAccount, userPassword);
        if (!result) {
            return new Response(4004);
        }
        User user = userService.getUser(userAccount);

        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        userService.refreshUserLoginContext(user.getId(), IpUtils.getIpAddress(request), BaseUtils.currentSeconds());

        UserInfoVo userInfo = new UserInfoVo();
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setUserAccount(user.getUserAccount());
        userInfo.setNickName(user.getNickName());
        userInfo.setEmail(user.getEmail());
        userInfo.setBirthday(BaseUtils.timeStamp2Date(user.getBirthday()));
        userInfo.setGender(user.getGender());
        userInfo.setUserIntro(user.getUserIntro());
        Integer userCurrentPoint = pointUserService.getPointByUserId(user.getId());
        userInfo.setUserCurrentPoint(userCurrentPoint);
        UserLoginInfoVo loginInfo = new UserLoginInfoVo();

        loginInfo.setSign(SignUtils.makeSign(user.getId()));
        loginInfo.setUserInfo(userInfo);

        return new Response(1001, loginInfo);
    }

    /**
     * 用户在app修改一些信息，修改成功后应该自动刷新userInfo页面
     */
    @RequestMapping("/user/edit")
    public Response userEdit(@VerifiedUser User loginUser,
                             @RequestParam(required = false)BigInteger id,
                             @RequestParam(required = false,name="userAccount")String userAccount,
                             @RequestParam(required = false,name="userPassword")String userPassword,
                             @RequestParam(required = false,name="avatar")String avatar,
                             @RequestParam(required = false,name="nickName")String nickName,
                             @RequestParam(required = false,name="gender")Integer gender,
                             @RequestParam(required = false,name="email")String email,
                             @RequestParam(required = false,name="userIntro")String userIntro,
                             @RequestParam(required = false,name="birthday")Integer birthday) {

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }

        try {
            BigInteger result = userService.editUser(id, userAccount, userPassword, avatar, nickName, gender, email, userIntro, birthday);
            return new Response(1001,result);
        }catch (Exception e){
            return new Response(4004);
        }
    }

    @RequestMapping("/user/watch/course")
    public Response userWatchCourse(@VerifiedUser User loginUser,
                                    @RequestParam(required = false)BigInteger userId,
                                    @RequestParam(required = false)BigInteger courseId,
                                    @RequestParam(required = false)BigInteger courseLessonDetailId){
        //先验证登录
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        try{
            String courseLessonLink = baseUserWatchService.verifyUserWatchCourse(userId,courseId,courseLessonDetailId);
            return new Response(1001,courseLessonLink);

        }catch(Exception e){
            return new Response(4004);
        }

    }

    @RequestMapping("/user/purchase/course")
    public Response userPurchaseCourse(@VerifiedUser User loginUser,
                                      @RequestParam(required = false)BigInteger courseId,
                                      @RequestParam(required = false)BigInteger userId
                                      ){

        //先验证登录
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        //再验证课程是否在架
        Course course = courseService.getAvailableCourseById(courseId);
        if(BaseUtils.isEmpty(course)){
            return new Response(4004);
        }

        try{

            //创建用户购买课程关系
            baseUserPurchasedCourseService.createUserPurchasedCourseRelation(userId,courseId);
            //创建购买课程订单

            //产生积分订单，积分变化记录，用户积分变化
            basePointService.purchasingCourseForGiftPoint(userId,course);

            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }

    }


    @RequestMapping("/user/sign/in") //用户签到送积分
    public Response userSingIn(@VerifiedUser User loginUser,
                               @RequestParam(required = false)BigInteger userId){

        //先验证登录
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        //然后判断用户今天是否已经签到,如果已经签到就不能签到了
        boolean hasSignedIn = BaseUtils.hasSignedInToday(userId);
        if(hasSignedIn==true){
            return new Response(1001);
        }
        //没有签到，然后从user_membership表里判断是否会员:只判断是否是会员1就行了；会员过期了是0，没买过会员查不到。
        Integer isMembership = userMemberShipService.getIsMembershipByUserId(userId);
        Integer addedPoint = 0;
        //会员签到积分+2，非会员签到积分+1
        if(BaseUtils.isEmpty(isMembership)){
            addedPoint = 2;
        }else {
            addedPoint = 1;
        }


        try{
            //产生积分订单，积分变化记录，用户积分变化
            basePointService.addPointForSingIn(userId,addedPoint);
            //在redis里写入当前用户的签到标识
            BaseUtils.signIn(userId);
            return new Response(1001);

        }catch (Exception e){
            return new Response(1002);
        }

    }

    //本月只分享一次获得100积分
    @RequestMapping("/user/share/course")
    public Response courseShare(@VerifiedUser User loginUser,
                                @RequestParam(required = false)BigInteger userId,
                                @RequestParam(required = false)BigInteger courseId,
                                @RequestParam(required = false)BigInteger shareId){

        //先验证登录
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }

        //验证是否这个月分享过，如果分享过不增加积分，直接返回ok，如果没有，就继续下一步
        boolean shareOrNot = BaseUtils.hasSharedInMonth(userId);
        if(shareOrNot == true){
            return new Response(1001);
        }

        //验证分享成功码是否在表中，如果是从分享成功表中获取 客户是通过哪种渠道分享的
        // 1.通过微信朋友圈分享 2.通过微博分享
        UserShareType userShareType = userShareTypeService.getById(shareId);
        if(BaseUtils.isEmpty(userShareType)){
            return new Response(1002);
        }
        //没分享过分享记录表增加一条数据，积分变化记录表增加一条数据，用户积分增加100
        try{
            basePointService.addPointForSharing(userId,courseId,userShareType);
            BaseUtils.shareIn(userId);
            return new Response(1001);
        }catch(Exception e){
            return new Response(1002);
        }
    }

}
