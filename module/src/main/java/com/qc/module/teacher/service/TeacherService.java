package com.qc.module.teacher.service;

import com.qc.module.teacher.entity.Teacher;
import com.qc.module.teacher.mapper.TeacherMapper;
import com.qc.module.user.service.UserService;
import com.qc.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService{

    @Resource
    private TeacherMapper mapper;

    private UserService userService;
    @Autowired
    public TeacherService(UserService userService){
        this.userService = userService;
    }

    public  List<Teacher> extractTeacherList(Integer pageNum, Integer pageSize,String realName){
        int begin = (pageNum-1)*pageSize;
        return mapper.extractTeacherList(begin,pageSize,realName);
    }

    public Teacher getById(BigInteger teacherId) {
        return mapper.getById(teacherId);
    }

    public Integer getTotal() {
        return mapper.getTotal();
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger edit(BigInteger id, BigInteger userId, String enrollmentTime, String realName) {

        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setUserId(userId);
        teacher.setEnrollmentTime(enrollmentTime);
        teacher.setRealName(realName);

        if(!BaseUtils.isEmpty(id)){
            Teacher oldTeacher = mapper.extractById(id);
            if(oldTeacher==null){
                throw new RuntimeException("This teacher does not exist");
            }
            int update = mapper.update(teacher);
            if(update==0){
                throw new RuntimeException("Update Failed!");
            }
            return id;
        }

        int now = BaseUtils.currentSeconds();
        teacher.setCreateTime(now);
        mapper.insert(teacher);
        return teacher.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(BigInteger id){
        Teacher oldTeacher = mapper.extractById(id);
        if(BaseUtils.isEmpty(oldTeacher)){
            throw new RuntimeException("This teacher does not exist");
        }
        int updateTime = BaseUtils.currentSeconds();
        return mapper.delete(id,updateTime);
    }

    public String getIdsByUserId(String nickName) {

        String userIdsForTeacher = userService.getUserIdsByNickName(nickName);

        if(userIdsForTeacher==null){
            return null;
        }
        List<BigInteger> teacherIdsByUserId = mapper.getTeacherIdsByUserId(userIdsForTeacher);
        StringBuilder sb = new StringBuilder();
        for(BigInteger x:teacherIdsByUserId){
            sb.append(x+",");
        }
        int len = sb.length();
        if(len==0){
            return "-1";
        }
        sb.delete(len-1,len);
        userIdsForTeacher =sb.toString();
        return userIdsForTeacher;
    }

    public List<Teacher> getTeachersByNickName(Integer pageNum,Integer pageSize, String nickName){
        Integer begin = (pageNum-1)* pageSize;
        String idsByUserId = getIdsByUserId(nickName);
        return mapper.getTeachersByNickName(begin,pageSize,idsByUserId);
    }

    public List<Teacher> getByIds(List<BigInteger> teacherIds){
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
