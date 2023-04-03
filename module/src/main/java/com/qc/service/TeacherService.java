package com.qc.service;

import com.qc.entity.Teacher;
import com.qc.mapper.TeacherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@Service
@Slf4j
public class TeacherService{

    @Resource
    private TeacherMapper mapper;

    private UserService userService;

    @Autowired
    private TeacherService(UserService userService){
        this.userService = userService;
    }

    public  List<Teacher> getTeachersForConsole(Integer pageNum, Integer pageSize){
        int begin = (pageNum-1)*pageSize;
        return mapper.getTeachersForConsole(begin,pageSize);
    }

    public Teacher getById(BigInteger teacherId) {
        return mapper.getById(teacherId);
    }

    public Integer getTotal() {
        return mapper.getTotal();
    }


    public BigInteger editTeacher(BigInteger id, BigInteger userId, String enrollmentTime, String realName) {

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setUserId(userId);
        teacher.setEnrollmentTime(enrollmentTime);
        teacher.setRealName(realName);

        if(id!=null){
            Teacher oldTeacher = mapper.extractById(id);
            if(oldTeacher==null){
                throw new RuntimeException("This teacher does not exist");
            }
            mapper.update(teacher);
            return id;
        }

        int now = (int)System.currentTimeMillis()/1000;
        teacher.setCreateTime(now);
        teacher.setIsDeleted(0);
        mapper.update(teacher);
        return (BigInteger) teacher.getId();
    }

    public int delete(BigInteger id){
        Teacher oldTeacher = mapper.extractById(id);
        if(oldTeacher==null){
            throw new RuntimeException("This teacher does not exist");
        }
        Integer updateTime = (int)System.currentTimeMillis()/1000;
        return mapper.delete(id,updateTime);
    }

    public String getIdsByUserId(String nickName) {

        String UserIdsForTeacher = userService.getUserIdsByNickName(nickName);

        if(UserIdsForTeacher==null){
            return null;
        }
        List<BigInteger> teacherIdsByUserId = mapper.getTeacherIdsByUserId(UserIdsForTeacher);
        StringBuilder sb = new StringBuilder();
        for(BigInteger x:teacherIdsByUserId){
            sb.append(x+",");
        }
        int len = sb.length();
        if(len==0){
            return "-1";
        }
        sb.delete(len-1,len);
        UserIdsForTeacher =sb.toString();
        return UserIdsForTeacher;
    }

    public List<Teacher> getTeachersByNickName(Integer pageNum,Integer pageSize, String nickName){
        Integer begin = (pageNum-1)* pageSize;
        String idsByUserId = getIdsByUserId(nickName);
        return mapper.getTeachersByNickName(begin,pageSize,idsByUserId);
    }

    public List<Teacher> getByIds(List<BigInteger> teacherIds){
        log.info(teacherIds.toString());
        StringBuilder ss = new StringBuilder();
        for(BigInteger x:teacherIds){
            ss.append(x+",");
        }
        int len = ss.length();
        if(len==0){
            return new ArrayList<>();
        }
        ss.delete(len-1,len);
        String teacherIdsFromCourse = ss.toString();
        List<Teacher> byIds = mapper.getByIds(teacherIdsFromCourse);
        return byIds;
    }

}
