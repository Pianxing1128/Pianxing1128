package com.qc.controller.course;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.course.CourseInfoVo;
import com.qc.module.course.entity.Course;
import com.qc.module.course.service.BaseCourseService;
import com.qc.module.course.service.CourseService;
import com.qc.module.course.service.CourseTagRelationService;
import com.qc.module.course.service.CourseTagService;
import com.qc.module.teacher.entity.Teacher;
import com.qc.module.teacher.service.TeacherService;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.BaseUtils;
import com.qc.utils.Response;
import com.qc.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseTagRelationService courseTagRelationService;
    @Autowired
    private CourseTagService courseTagService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseCourseService baseCourseService;

    @RequestMapping("course/list")
    public Response courseList(@VerifiedUser User loginUser,
                               @RequestParam(required = false,name = "pageNum")Integer inputPageNum,
                               @RequestParam(required = false, name = "courseName") String courseName,
                               @RequestParam(required = false, name = "nickName") String nickName,
                               @RequestParam(required = false, name = "showTagId") Integer showTagId,
                               @RequestParam(required = false, name = "orderedType")Integer orderedType,
                               @RequestParam(required = false, name = "isVip")Integer isVip){

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }

        Integer pageNum;
        if(inputPageNum==null || inputPageNum<=0){
            pageNum=1;
        }else {
            pageNum = inputPageNum;
        }
        Integer pageSize = Integer.valueOf(SpringUtils.getProperty("application.pagesize"));
        BaseListVo result = new BaseListVo();
        List<Course> courseList = baseCourseService.getCourseByCourseNameAndNickNameAndShowTagIdAndOrderedTypeAndIsVip(pageNum,pageSize,courseName,nickName,
                                                                                                                        showTagId,orderedType,isVip);
        if(courseList.size()==0){
            return new Response(4004);
        }
        List<BigInteger> teacherIds = courseList.stream().map(Course::getTeacherId).collect(Collectors.toList());
        List<Teacher> teacherList = teacherService.getByIds(teacherIds);
        Map<BigInteger, Teacher> idTeacherMap = teacherList.stream().collect(Collectors.toMap(Teacher::getId, Function.identity()));
        List<BigInteger> userIds = teacherList.stream().map(Teacher::getUserId).collect(Collectors.toList());
        List<User> userList = userService.getByIds(userIds);
        Map<BigInteger, User> idUserMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        List<CourseInfoVo> courseVoList = new ArrayList<>();
        for(Course c:courseList){
            CourseInfoVo courseVo = new CourseInfoVo();
            if(idTeacherMap.containsKey(c.getTeacherId())){
                Teacher teacher = idTeacherMap.get(c.getTeacherId());
                courseVo.setRealName(teacher.getRealName());
                if(idUserMap.containsKey(teacher.getUserId())){
                    User user = idUserMap.get(teacher.getUserId());
                    courseVo.setTeacherAvatar(user.getAvatar());
                    courseVo.setNickName(user.getNickName());
                    courseVo.setTeacherIntro(user.getUserIntro());
                }else {
                    continue;
                }
            }else {
                continue;
            }
            courseVo.setId(c.getId());
            courseVo.setTeacherId(c.getTeacherId());
            courseVo.setCourseName(c.getCourseName());
            courseVo.setCourseSubName(c.getCourseSubName());
            courseVo.setCourseCount(c.getCourseCount());
            courseVo.setCourseTime(c.getCourseTime());
            courseVo.setCourseIntro(c.getCourseIntro());
            courseVo.setCoursePrice("￥"+c.getCoursePrice());
            courseVo.setWeight(c.getWeight());
            courseVo.setIsVip(c.getIsVip());
            courseVo.setIsMarketable(c.getIsMarketable());
            courseVo.setPurchasedTotal(c.getPurchasedTotal());
            courseVo.setUpdateTime(BaseUtils.timeStamp2Date(c.getUpdateTime()));
            courseVo.setCreateTime(BaseUtils.timeStamp2Date(c.getCreateTime()));
            courseVo.setIsDeleted(c.getIsDeleted());

            try{
                courseVo.setCourseImages(Arrays.asList((c.getCourseImage().split("\\$"))));
            }catch (Exception e){
                courseVo.setCourseImages(null);
            }

            String tagIds = courseTagRelationService.getTagIds(c.getId());
            List<String> tagList = courseTagService.getTagsByTagIds(tagIds);
            courseVo.setTags(tagList);
            courseVoList.add(courseVo);
        }
        result.setCourseList(courseVoList);
        result.setCourseTotal(courseService.getTotal());
        result.setPageSize(pageSize);
        return new Response(1001,result);
    }

    @RequestMapping("/course/info")
    public Response showCourseDetailById(@VerifiedUser User loginUser,
                                         @RequestParam(name="id") BigInteger id) {

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        Course course = courseService.getById(id);
        if(BaseUtils.isEmpty(course)){
            return new Response(3001);
        }
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(!BaseUtils.isEmpty(teacher)){
            courseInfoVo.setRealName(teacher.getRealName());
        }
        User user = userService.getById(teacher.getUserId());
        if(!BaseUtils.isEmpty(user)){
            courseInfoVo.setNickName(user.getNickName());
            courseInfoVo.setTeacherIntro(user.getUserIntro());
        }
        //根据id = courseId 得到 tagId, 根据tagId = tag表里的id 获得 tag
        String tagIds = courseTagRelationService.getTagIds(id);
        if(!BaseUtils.isEmpty(tagIds)){
            List<String> tags = courseTagService.getTagsByTagIds(tagIds);
            if(!BaseUtils.isEmpty(tags)){
                courseInfoVo.setTags(tags);
            }
        }
        courseInfoVo.setCourseName(course.getCourseName());
        courseInfoVo.setCourseSubName(course.getCourseSubName());
        courseInfoVo.setCourseCount(course.getCourseCount());
        courseInfoVo.setCourseTime(course.getCourseTime());
        courseInfoVo.setCourseIntro(course.getCourseIntro());
        courseInfoVo.setCoursePrice("￥"+course.getCoursePrice());
        List<String> courseList = Arrays.asList(course.getCourseImage().split("\\$"));
        courseInfoVo.setCourseImages(courseList);
        courseInfoVo.setWeight(course.getWeight());
        courseInfoVo.setIsVip(course.getIsVip());
        courseInfoVo.setIsMarketable(course.getIsMarketable());
        courseInfoVo.setPurchasedTotal(course.getPurchasedTotal());
        courseInfoVo.setUpdateTime(BaseUtils.timeStamp2Date(course.getUpdateTime()));
        courseInfoVo.setCreateTime(BaseUtils.timeStamp2Date(course.getCreateTime()));
        courseInfoVo.setIsDeleted(course.getIsDeleted());

        return new Response(1001,courseInfoVo);
    }

    @RequestMapping("/course/insert")
    public Response courseInsert(@VerifiedUser User loginUser,
                                 @RequestParam(required = false,name = "id")BigInteger id,
                                 @RequestParam(name="teacherId") BigInteger teacherId,
                                 @RequestParam(name="courseName")String courseName,
                                 @RequestParam(name = "courseIntro")String courseIntro,
                                 @RequestParam(name = "coursePrice")Integer coursePrice,
                                 @RequestParam(name = "courseCount")String courseCount,
                                 @RequestParam(name = "courseSubName")String courseSubName,
                                 @RequestParam(name = "isVip")Integer isVip,
                                 @RequestParam(name = "isMarketable")Integer isMarketable,
                                 @RequestParam(required = false,name = "purchasedTotal")Integer purchasedTotal,
                                 @RequestParam(required = false,name = "courseImage")String courseImage,
                                 @RequestParam(required = false,name = "courseTime")String courseTime,
                                 @RequestParam(required = false,name = "weight")Integer weight,
                                 @RequestParam(required = false,name = "tags") String tags) {

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002); //需要登陆
        }
        //必填信息
        courseName = courseName.trim();
        courseSubName = courseSubName.trim();
        courseIntro = courseIntro.trim();
        courseCount = courseCount.trim();
        //非必填信息
        if(!BaseUtils.isEmpty(courseTime)){  //总时常这里输入 未完结 或者 x小时x分钟
        courseTime = courseTime.trim();
        }
        if(BaseUtils.isEmpty(courseName) || BaseUtils.isEmpty(courseSubName) || BaseUtils.isEmpty(courseIntro)
                || BaseUtils.isEmpty(coursePrice) || BaseUtils.isEmpty(courseCount) || BaseUtils.isEmpty(isVip) || BaseUtils.isEmpty(isMarketable)){
            return new Response(3051); //必填信息不能为空
        }
        Teacher teacher = teacherService.getById(teacherId);
        if (BaseUtils.isEmpty(teacher)){
            return new Response(3052); // 老师Id不存在
        }
        try{
             baseCourseService.editCourse(id,teacherId, courseName,courseSubName,courseCount, courseTime, courseIntro, courseImage, coursePrice,
                     isVip,isMarketable,purchasedTotal,weight,tags);

            return new Response(1001); // ok
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/course/update")
    public Response courseUpdate(@VerifiedUser User loginUser,
                                 @RequestParam(name="id") BigInteger id,
                                 @RequestParam(required = false,name = "teacherId")BigInteger teacherId,
                                 @RequestParam(required = false,name = "courseName")String courseName,
                                 @RequestParam(required = false,name = "courseSubName")String courseSubName,
                                 @RequestParam(required = false,name = "courseCount")String courseCount,
                                 @RequestParam(required = false,name = "courseTime")String courseTime,
                                 @RequestParam(required = false,name = "courseIntro")String courseIntro,
                                 @RequestParam(required = false,name = "courseImage")String courseImage,
                                 @RequestParam(required = false,name = "coursePrice")Integer coursePrice,
                                 @RequestParam(required = false,name = "weight")Integer weight,
                                 @RequestParam(required = false,name = "isVip")Integer isVip,
                                 @RequestParam(required = false,name = "isMarketable")Integer isMarketable,
                                 @RequestParam(required = false,name = "purchasedTotal")Integer purchasedTotal,
                                 @RequestParam(required = false,name = "tags") String tags) {

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002); //需要登陆
        }
        if(!BaseUtils.isEmpty(teacherId)){
            Teacher teacher = teacherService.getById(teacherId);
            if(BaseUtils.isEmpty(teacher)){
                return new Response(3052); // 老师Id不存在
            }
        }

        try {
            baseCourseService.editCourse(id, teacherId, courseName, courseSubName, courseCount, courseTime, courseIntro, courseImage, coursePrice,isVip,isMarketable,purchasedTotal,weight,tags);
            return new Response(1001); // ok
        }catch (RuntimeException e){
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/course/delete")
    public Response courseDelete(@VerifiedUser User loginUser,
                                 @RequestParam(name="id") BigInteger id){

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        try {
            baseCourseService.delete(id);
            return new Response(1001);
        }catch (RuntimeException e){
            return new Response(4004);
        }
    }
}