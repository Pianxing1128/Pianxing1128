package com.qc.module.course.service;

import com.qc.module.appIndexTagIdRelation.service.AppIndexTagIdRelationService;
import com.qc.module.course.entity.Course;
import com.qc.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class BaseCourseService {
    /**
     * The Spring team generally advocates constructor injection as it enables one to implement application components as immutable objects and to ensure that required dependencies are not null. Furthermore constructor-injected components are always returned to client (calling) code in a fully initialized state.
     * 这个构造器注入的方式啊，能够保证注入的组件不可变，并且确保需要的依赖不为空。此外，构造器注入的依赖总是能够在返回客户端（组件）代码的时候保证完全初始化的状态。
     */

    private CourseTagRelationService courseTagRelationService;
    private CourseService courseService;
    private CourseTagService courseTagService;
    private AppIndexTagIdRelationService appIndexTagIdRelationService;
    @Autowired
    public BaseCourseService(CourseTagRelationService courseTagRelationService,CourseService courseService,CourseTagService courseTagService,
                             AppIndexTagIdRelationService appIndexTagIdRelationService){
        this.courseTagRelationService = courseTagRelationService;
        this.courseService = courseService;
        this.courseTagService = courseTagService;
        this.appIndexTagIdRelationService=appIndexTagIdRelationService;
    };

    @Transactional(rollbackFor = Exception.class)
    public BigInteger editCourse(BigInteger id, BigInteger teacherId, String courseName, String courseSubName, String courseCount, String courseTime,String courseIntro, String courseImage, Integer coursePrice, Integer isVip,Integer isMarketable, Integer purchasedTotal,Integer weight, String tags){

            BigInteger courseId =courseService.edit(id, teacherId, courseName, courseSubName, courseCount, courseTime, courseIntro, courseImage, coursePrice,isVip,isMarketable,purchasedTotal,weight);
            List<BigInteger> tagsList = courseTagService.editTags(tags);
            courseTagRelationService.editCourseTagRelation(courseId, tagsList);
            return courseId;
    }

    public List<Course> getCourseByCourseNameAndNickNameAndShowTagIdAndOrderedTypeAndIsVip(Integer pageNum, Integer pageSize, String courseName, String nickName,
                                                                                           Integer showTagId,Integer orderedType,Integer isVip) {

        String tagIds = appIndexTagIdRelationService.getTagIdsByShowTagId(showTagId);
        String courseIds = null;
        if(!BaseUtils.isEmpty(tagIds)){
             courseIds = courseTagRelationService.getCourseIds(tagIds);
        }
        String orderedName =null;
        if(orderedType==null){
            orderedName="id";
        }else if(orderedType==1){
            orderedName = "create_time desc";
        }else if(orderedType==2){
            orderedName = "purchased_total desc";
        }else if(orderedType==3){
            orderedName = "course_price desc";
        }else if(orderedType==4){
            orderedName = "course_price asc";
        }

        List<Course> courseList = courseService.getCourseByCourseNameAndNickNameAndShowTagId(pageNum, pageSize, courseName, nickName, courseIds,orderedName,isVip);
        return courseList;
        }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCourse(BigInteger id){
        courseService.delete(id);
        courseTagRelationService.deleteByCourseId(id);
    }

}
