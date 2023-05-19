package com.qc.module.course.service;

import com.qc.module.course.entity.Course;
import com.qc.module.course.entity.NewCourse;
import com.qc.module.course.mapper.CourseMapper;
import com.qc.module.teacher.entity.Teacher;
import com.qc.module.teacher.service.TeacherService;
import com.qc.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
@Primary
public class CourseService {

    @Resource
    private CourseMapper mapper;

    private TeacherService teacherService;
    @Autowired
    public CourseService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public List<Course> getCoursesForApp(Integer pageNum, Integer pageSize) {
        int begin = (pageNum - 1) * pageSize;
        return mapper.getCoursesForApp(begin, pageSize);
    }

    public Course getById(BigInteger id){
        return mapper.getById(id);
    }

    public Course extractCourseById(BigInteger id){
        return mapper.extractById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger edit(BigInteger id, BigInteger teacherId, String courseName, String courseSubName, String courseCount, String courseTime, String courseIntro,
                                     String courseImage, String coursePrice, Integer weight){

        Course course = new Course();
        course.setId(id);
        course.setTeacherId(teacherId);
        course.setCourseName(courseName);
        course.setCourseSubName(courseSubName);
        course.setCourseCount(courseCount);
        course.setCourseTime(courseTime);
        course.setCourseIntro(courseIntro);
        if(BaseUtils.isEmpty(courseImage)){ //课程默认图片
            course.setCourseImage("https://qc1128.oss-cn-shenzhen.aliyuncs.com/image/2023/04/02/82f38d9d412b40bf86175fcc32467a58_3840x2160.jpg");
        }else {
            course.setCourseImage(courseImage);
        }
        course.setCoursePrice(coursePrice);
        course.setWeight(weight);

        if(!BaseUtils.isEmpty(id)) {
            Course oldCourse = mapper.extractById(id);
            if (BaseUtils.isEmpty(oldCourse)) {
                throw new RuntimeException("The course does not exist!");
            }
            BigInteger oldCourseTeacherId = oldCourse.getTeacherId();
            Teacher teacher = teacherService.getById(oldCourseTeacherId);
            if (BaseUtils.isEmpty(teacher)) {
                throw new RuntimeException("The teacher does not exist!");
            }
            int update = mapper.update(course);
            if(update==0){
                throw new RuntimeException("Update failed!");
            }
            return id;
        }
        int now = BaseUtils.currentSeconds();
        course.setCreateTime(now);
        mapper.insert(course);
        return course.getId();
    }


    public BigInteger delete(BigInteger id){
        Course oldCourse = mapper.extractById(id);
        if(oldCourse==null){
            throw new RuntimeException("This course does not exist!");
        }
        int updateTime = BaseUtils.currentSeconds();
        mapper.delete(id,updateTime);
        return id;
    }

    public Integer getTotal() {
        return mapper.getTotal();
    }

    public List<Course> getCourseByCourseNameAndNickNameAndTag(Integer pageNum, Integer pageSize,String courseName,String nickName,String courseIds) {

        Integer begin = (pageNum - 1) * pageSize;
        String idsByTeacherId = teacherService.getIdsByUserId(nickName);
        return mapper.getCoursesByCourseNameAndNickNameAndTag(begin,pageSize,courseName,idsByTeacherId,courseIds);
    }

    public List<NewCourse> getCourseByRealName(@Param("pageNum") Integer pageNum, @Param("size") Integer pageSize, String realName, String nickName){
        Integer begin = (pageNum-1)*pageSize;
        return mapper.getCourseTeacherUserByRealNameAndNickName(begin,pageSize,realName,nickName);
    }
}
