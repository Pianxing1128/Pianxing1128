package com.qc.module.user.service;

import com.qc.module.course.service.CourseService;
import com.qc.module.courseLessonDetail.entity.CourseLessonDetail;
import com.qc.module.courseLessonDetail.service.CourseLessonDetailService;
import com.qc.module.userMembership.service.UserMemberShipService;
import com.qc.module.userPurchasedCourseRelation.service.UserPurchasedCourseRelationService;
import com.qc.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class BaseUserWatchService {

    private CourseService courseService;
    private UserMemberShipService userMemberShipService;
    private UserPurchasedCourseRelationService userPurchasedCourseRelationService;
    private CourseLessonDetailService courseLessonDetailService;

    @Autowired
    public BaseUserWatchService(CourseService courseService, UserMemberShipService userMemberShipService, UserPurchasedCourseRelationService userPurchasedCourseRelationService,
                                CourseLessonDetailService courseLessonDetailService){
        this.courseService = courseService;
        this.userMemberShipService = userMemberShipService;
        this.userPurchasedCourseRelationService = userPurchasedCourseRelationService;
        this.courseLessonDetailService = courseLessonDetailService;
    }

    public String verifyUserWatchCourse(BigInteger userId, BigInteger courseId, BigInteger courseLessonDetailId){

        //先验证课程类型，如果课程未下架,is_marketable = 1
        Integer courseType = courseService.getCourseTypeById(courseId);

        if(BaseUtils.isEmpty(courseType)){
            throw new RuntimeException("This course does not exist!");
        }
        //先验证课程小节链接是否未下架,is_marketable = 1
        CourseLessonDetail courseLessonDetail  = courseLessonDetailService.getAvailableCourseLessonDetailById(courseLessonDetailId);
        if(BaseUtils.isEmpty(courseLessonDetail)){
            throw new RuntimeException("This Lesson does not exist!");
        }
        String lessonLink = courseLessonDetail.getLessonLink();
        //如果是免费课程，可以正常观看
        if(courseType == 0){
            return lessonLink;
        //如果是会员课程，先验证当前小节课是否可以试看，如果可以返回播放链接；如果不可以，验证是否会员，是会员返回链接，不是则返回无法播放
        }else if(courseType == 1){

            Integer lessonType = courseLessonDetail.getLessonType();
            if(lessonType == 0){
                return lessonLink;
            }
            Integer isMembership = userMemberShipService.getIsMembershipByUserId(userId);
            if(isMembership == 1){
                return lessonLink;
            }else{
                throw new RuntimeException("In order to watch this lesson, please buy a membership!");
            }
        //如果是必须购买课程,先从购买记录表查询对应userId的所有courseIdList，判断courseId是否在这里面
        }else{
            Integer lessonType = courseLessonDetail.getLessonType();
            if(lessonType == 0){
                return lessonLink;
            }
            List<BigInteger> purchasedCourseIdList = userPurchasedCourseRelationService.getCourseIdByUserId(userId);
            if(purchasedCourseIdList.contains(courseId)){
                return lessonLink;
            }else{
                throw new RuntimeException("In order to watch this lesson, please buy this course!");
                }
            }
    }
}


