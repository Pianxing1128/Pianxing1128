package com.qc.service;

import com.qc.entity.Course;
import com.qc.entity.NewCourse;
import com.qc.entity.Teacher;
import com.qc.mapper.CourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class CourseService {

    @Resource
    private CourseMapper mapper;

    private TeacherService teacherService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    public CourseService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public List<Course> getCoursesForApp(Integer pageNum, Integer pageSize) {
        int begin = (pageNum - 1) * pageSize;
        return mapper.getCoursesForApp(begin, pageSize);
    }

    public List<Course> getCoursesForConsole(Integer pageNum, Integer pageSize) {
        int begin = (pageNum - 1) * pageSize;
        return mapper.getCoursesForConsole(begin, pageSize);
    }

    public Course getById(BigInteger id){
        Course oldCourse = mapper.getById(id);
        if (oldCourse == null) {
            throw new RuntimeException("The course does not exist");
        }
        threadService.updateCourse(this.mapper,id);
        return mapper.getById(id);
    }

    public Course extractCourseById(BigInteger id){
        return mapper.extractById(id);
    }

    public BigInteger editCourse(BigInteger id, BigInteger teacherId, String courseName, String courseSubName, String courseCount, String courseTime, String courseIntro,
        String courseImage, String coursePrice, Integer weight){

        Course course = new Course();
        course.setId(id);
        course.setTeacherId(teacherId);
        course.setCourseName(courseName);
        course.setCourseSubName(courseSubName);
        course.setCourseCount(courseCount);
        course.setCourseTime(courseTime);
        course.setCourseIntro(courseIntro);
        if(courseImage==null){
            course.setCourseImage("https://qc1128.oss-cn-shenzhen.aliyuncs.com/image/2023/04/02/82f38d9d412b40bf86175fcc32467a58_38402160.jpg");
        }else {
            course.setCourseImage(courseImage);
        }
        course.setCoursePrice(coursePrice);
        course.setWeight(weight);

        if(id!=null) {
            Course oldCourse = mapper.extractById(id);
            if (oldCourse == null) {
                throw new RuntimeException("The course does not exist");
            }
            BigInteger oldCourseTeacherId = oldCourse.getTeacherId();
            Teacher teacher = teacherService.getById(oldCourseTeacherId);
            if (teacher == null) {
                throw new RuntimeException("The teacher does not exist");
            }
            mapper.update(course);
            return id;
        }
        int now = (int)System.currentTimeMillis()/1000;
        course.setCreateTime(now);
        course.setIsDeleted(0);
        mapper.insert(course);
        return course.getId();
    }

    public int delete(BigInteger id){
        Course oldCourse = mapper.extractById(id);
        if(oldCourse==null){
            throw new RuntimeException("This course does not exist!");
        }
        Integer updateTime = (int)System.currentTimeMillis()/1000;
        return mapper.delete(id,updateTime);
    }

    public Integer getTotal() {
        return mapper.getTotal();
    }

    public List<Course> getCourseByCourseNameAndNickName(Integer pageNum, Integer pageSize,String courseName,String nickName) {

        Integer begin = (pageNum - 1) * pageSize;
        String idsByTeacherId = teacherService.getIdsByUserId(nickName);
        return mapper.getCoursesByCourseNameAndNickName(begin,pageSize,courseName,idsByTeacherId);
    }

    public List<NewCourse> getCourseByRealName(@Param("pageNum") Integer pageNum, @Param("size") Integer pageSize, String realName, String nickName){
        Integer begin = (pageNum-1)*pageSize;
        log.info(mapper.getCourseTeacherUserByRealNameAndNickName(begin,pageSize,realName,nickName).toString());
        return mapper.getCourseTeacherUserByRealNameAndNickName(begin,pageSize,realName,nickName);
    }
}
