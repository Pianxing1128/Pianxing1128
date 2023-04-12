package com.qc.controller.course;

import com.qc.domain.BaseListVo;
import com.qc.domain.course.CourseVo;
import com.qc.module.course.entity.Course;
import com.qc.module.teacher.entity.Teacher;
import com.qc.module.user.entity.User;
import com.qc.module.course.service.CourseService;
import com.qc.module.teacher.service.TeacherService;
import com.qc.module.user.service.UserService;
import com.qc.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CourseConsoleController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    @RequestMapping("course/list")
    public Response courseList(@RequestParam(required = false,name = "pageNum")Integer inputPageNum){

        Integer pageNum;
        if(inputPageNum==null || inputPageNum<=0){
            pageNum=1;
        }else {
            pageNum = inputPageNum;
        }
        int pageSize=3;

        BaseListVo result = new BaseListVo();
        List<Course> courseList = courseService.getCoursesForConsole(pageNum,pageSize);
        if(courseList.size()==0){
            return new Response(4004);
        }
        List<BigInteger> teacherIds = courseList.stream().map(Course::getTeacherId).collect(Collectors.toList());
        List<Teacher> teacherList = teacherService.getByIds(teacherIds);
        Map<BigInteger, Teacher> idTeacherMap = teacherList.stream().collect(Collectors.toMap(Teacher::getId, Function.identity())); //得到 id，Teacher
        List<BigInteger> userIds = teacherList.stream().map(Teacher::getUserId).collect(Collectors.toList());
        List<User> userList = userService.getByIds(userIds);
        Map<BigInteger, User> idUserMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        List<CourseVo> courseVoList = new ArrayList<>();
        for(Course c:courseList){
            CourseVo courseVo = new CourseVo();
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
            courseVo.setCoursePrice(c.getCoursePrice());
            courseVo.setWeight(c.getWeight());
            courseVo.setCreateTime(c.getCreateTime().toString());
            int now = (int)(System.currentTimeMillis()/1000);
            courseVo.setUpdateTime(String.valueOf(now));
            courseVo.setIsDeleted(c.getIsDeleted());

            try{
                courseVo.setCourseImages(Arrays.asList((c.getCourseImage().split("\\$"))));
            }catch (Exception e){
                courseVo.setCourseImages(null);
            }
            courseVoList.add(courseVo);
        }
        result.setCourseList(courseVoList);
        result.setCourseTotal(courseService.getTotal());
        result.setPageSize(pageSize);
        return new Response(1001,result);
    }

    /**
     * 在新增的时候，要求必须要输入的属性是：teacherId,courseName,courseCount,courseIntro,coursePrice
     * 新增的时候，如果课程图片没有，用公司默认的一张课程图片
     */

    @RequestMapping("/course/insert")
    public Response courseInsert(@RequestParam(required = false,name = "id")BigInteger id,
                                     @RequestParam(name="teacherId") BigInteger teacherId,
                                     @RequestParam(name="courseName")String courseName,
                                     @RequestParam(name = "courseIntro")String courseIntro,
                                     @RequestParam(name = "coursePrice")String coursePrice,
                                     @RequestParam(name = "courseCount")String courseCount,
                                     @RequestParam(required = false,name = "courseImage")String courseImage,
                                     @RequestParam(required = false,name = "courseSubName")String courseSubName,
                                     @RequestParam(required = false,name = "courseTime")String courseTime,
                                     @RequestParam(required = false,name = "weight")Integer weight) {

        try{
            BigInteger newId = courseService.editCourse(id,teacherId, courseName,courseSubName,courseCount, courseTime, courseIntro, courseImage, coursePrice,
                    weight);

            return new Response(1001,newId);

        }catch(RuntimeException e) {
            return new Response(4004);
        }
    }

    @RequestMapping("/course/update")
    public Response courseUpdate(@RequestParam(name="id") BigInteger id,
                               @RequestParam(required = false)BigInteger teacherId,
                               @RequestParam(required = false)String courseName,
                               @RequestParam(required = false)String courseSubName,
                               @RequestParam(required = false)String courseCount,
                               @RequestParam(required = false)String courseTime,
                               @RequestParam(required = false)String courseIntro,
                               @RequestParam(required = false)String courseImage,
                               @RequestParam(required = false)String coursePrice,
                               @RequestParam(required = false)Integer weight) {

        try {
            BigInteger newId = courseService.editCourse(id, teacherId, courseName, courseSubName, courseCount, courseTime, courseIntro,
                    courseImage, coursePrice, weight);

            return new Response(1001,newId);
        }catch (RuntimeException e){
            return new Response(4004);
        }
    }

        @RequestMapping("/course/delete")
        public Response courseDelete(BigInteger id){
            /**
             如果输入的id查询不到，就会报错
             */
            try {
                int newId = courseService.delete(id);
                return new Response(1001,newId);
            }catch (RuntimeException e){
                return new Response(4004);
            }
    }
}