package com.qc.controller;

import com.qc.domain.BaseListVo;
import com.qc.domain.CourseVo;
import com.qc.entity.Course;
import com.qc.entity.Teacher;
import com.qc.entity.User;
import com.qc.mapper.CourseMapper;
import com.qc.service.CourseService;
import com.qc.service.TeacherService;
import com.qc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public BaseListVo courseList(@RequestParam(name = "pageNum") Integer pageNum){

        int pageSize=3;

        BaseListVo result = new BaseListVo();
        List<Course> courses = courseService.getCoursesForConsole(pageNum,pageSize);
        List<CourseVo> list = new ArrayList<>();

        for(Course c:courses){

            CourseVo entry = new CourseVo();

            Teacher teacher = teacherService.getById(c.getTeacherId());
            if(teacher==null){
                continue;
            }
            User user = userService.getById(teacher.getUserId());
            if(user == null){
                continue;
            }
            entry.setTeacherAvatar(user.getAvatar());
            entry.setNickName(user.getNickName());
            entry.setTeacherIntro(user.getUserIntro());
            entry.setRealName(teacher.getRealName());
            entry.setId(c.getId());
            entry.setTeacherId(c.getTeacherId());
            entry.setCourseName(c.getCourseName());
            entry.setCourseSubName(c.getCourseSubName());
            entry.setCourseCount(c.getCourseCount());
            entry.setCourseTime(c.getCourseTime());
            entry.setCourseIntro(c.getCourseIntro());

            try{
                entry.setCourseImages(Arrays.asList((c.getCourseImage().split("\\$"))));
            }catch (Exception e){
                log.info("有的课程图片不存在");
            }

            entry.setCoursePrice(c.getCoursePrice());

            entry.setWeight(c.getWeight());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Long time1 = Long.valueOf(c.getCreateTime()+"000");
            String creteTime = sdf.format(new Date(time1));
            entry.setCreateTime(creteTime);

            Long time2 = Long.valueOf(c.getUpdateTime()+"000");
            String updateTime = sdf.format(new Date(time2));
            entry.setUpdateTime(updateTime);

            entry.setIsDeleted(c.getIsDeleted());
            list.add(entry);
        }
        result.setCourseList(list);
        result.setCourseTotal(courseService.getTotal());
        result.setPageSize(pageSize);

        return result;

    }

    @RequestMapping("/course/insert")
    public Object courseInsert(@RequestParam(required = false)BigInteger id,
                               @RequestParam(name="teacherId") BigInteger teacherId,
                               @RequestParam(name="courseName")String courseName,
                               @RequestParam(required = false)String courseSubName,
                               @RequestParam(name = "courseCount")String courseCount,
                               @RequestParam(required = false)String courseTime,
                               @RequestParam(name = "courseIntro")String courseIntro,
                               @RequestParam(name = "courseImage")String courseImage,
                               @RequestParam(name = "coursePrice")String coursePrice,
                               @RequestParam(required = false)Integer weight) {

        try{
            Object i = courseService.editCourse(id,teacherId, courseName,courseSubName,courseCount, courseTime, courseIntro, courseImage, coursePrice,
                    weight);
            return i;
        }catch(RuntimeException e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/course/update")
    public Object courseUpdate(@RequestParam(name="id") BigInteger id,
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
            courseService.editCourse(id, teacherId, courseName, courseSubName, courseCount, courseTime, courseIntro,
                    courseImage, coursePrice, weight);
            log.info("修改后:"+ courseService.extractCourseById(id));
            return "修改成功";
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

    @RequestMapping("/course/delete")
    public String courseDelete(BigInteger id){

        try {
            courseService.delete(id);
            log.info("逻辑删除后:"+ courseService.extractCourseById(id));
            return "删除成功";
        }catch (RuntimeException e){
            return e.getMessage();
            }
    }
}