package com.qc.module.course.service;

import com.qc.module.course.entity.Course;
import com.qc.module.course.entity.NewCourse;
import com.qc.module.teacher.entity.Teacher;
import com.qc.module.course.mapper.CourseMapper;
import com.qc.module.teacher.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CourseService {

    @Resource
    private CourseMapper mapper;
    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseTagService courseTagService;
    @Resource
    private CourseTagRelationService courseTagRelationService;
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
    public String editCourse(BigInteger id, BigInteger teacherId, String courseName, String courseSubName, String courseCount, String courseTime, String courseIntro,
                                   String courseImage, String coursePrice, Integer weight, String tags){

        Course course = new Course();
        course.setId(id);
        course.setTeacherId(teacherId);
        course.setCourseName(courseName);
        course.setCourseSubName(courseSubName);
        course.setCourseCount(courseCount);
        course.setCourseTime(courseTime);
        course.setCourseIntro(courseIntro);
        if(courseImage==null){
            course.setCourseImage("https://qc1128.oss-cn-shenzhen.aliyuncs.com/image/2023/04/02/82f38d9d412b40bf86175fcc32467a58_3840x2160.jpg");
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
            return ("修改的课程为："+id);
        }
        int now = (int)(System.currentTimeMillis()/1000);
        course.setCreateTime(now);
        course.setIsDeleted(0);
        if(tags!=null){
            try {
                mapper.insert(course);
                BigInteger courseId = course.getId();
                List<String> tagList = Arrays.asList(tags.split("\\$"));
                int len = tagList.size();
                BigInteger tagId = null;
                String result = null;
                List<String> list = new ArrayList<>();
                if(len != 0) {
                    for (String tag : tagList) {
                        result = courseTagService.insertTag(tag, courseId);
                        list.add(result);
                    }
                }
                return list.toString();
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "全部新增失败";
            }
        }else {
            mapper.insert(course);
            BigInteger courseId = course.getId();
            String result = "新增的课程id为:"+ courseId +"，且无标签";
            return result;
    }


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

    public List<Course> getCourseByCourseNameAndNickNameAndTag(Integer pageNum, Integer pageSize,String courseName,String nickName,String tag,Integer isDeleted) {

        Integer begin = (pageNum - 1) * pageSize;
        String idsByTeacherId = teacherService.getIdsByUserId(nickName);
        String idsByTag = courseTagService.getIdsByTag(tag);
        String courseIds = courseTagRelationService.getCourseIds(idsByTag);
        return mapper.getCoursesByCourseNameAndNickNameAndTag(begin,pageSize,courseName,idsByTeacherId,courseIds,isDeleted);
    }

    public List<NewCourse> getCourseByRealName(@Param("pageNum") Integer pageNum, @Param("size") Integer pageSize, String realName, String nickName){
        Integer begin = (pageNum-1)*pageSize;
        return mapper.getCourseTeacherUserByRealNameAndNickName(begin,pageSize,realName,nickName);
    }
}
