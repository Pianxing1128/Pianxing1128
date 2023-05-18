package com.qc.controller.teacher;

import com.qc.domain.BaseListVo;
import com.qc.domain.teacher.TeacherVo;
import com.qc.module.teacher.entity.Teacher;
import com.qc.module.teacher.service.TeacherService;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qc112
 */
@RestController
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @RequestMapping("/teacher/list")
    public BaseListVo teacherList(@RequestParam(name = "pageNum") Integer pageNum){
        int pageSize = 2;
        BaseListVo result = new BaseListVo();
        List<Teacher> teachers = teacherService.getTeachersForConsole(pageNum,pageSize);
        List<TeacherVo> list = new ArrayList<>();

        for(Teacher t:teachers){
            TeacherVo entry = new TeacherVo();

            User user = userService.getById(t.getUserId());
            if(user==null){
                continue;
            }
            entry.setId(t.getId());
            entry.setUserId(t.getUserId());
            entry.setEnrollmentTime(t.getEnrollmentTime());
            entry.setRealName(t.getRealName());
            entry.setIsDeleted(t.getIsDeleted());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Long time1 = Long.valueOf(t.getCreateTime()+"000");
            String creteTime = sdf.format(new Date(time1));
            entry.setCreateTime(creteTime);

            Long time2 = Long.valueOf(t.getUpdateTime()+"000");
            String updateTime = sdf.format(new Date(time2));
            entry.setUpdateTime(updateTime);

            list.add(entry);
        }
        result.setTeacherTotal(teacherService.getTotal());
        result.setPageSize(pageSize);
        result.setTeacherList(list);
        return result;
    }

    @RequestMapping("/teacher/id")
    public Teacher getTeacherById(BigInteger id){
        return teacherService.getById(id);
    }

    @RequestMapping("teacher/total")
    public Integer getTeacherTotal(){
        return teacherService.getTotal();
    }

    @RequestMapping("/teacher/update")
    public String teacherUpdate(@RequestParam(name="id")BigInteger id,
                                @RequestParam(required = false)BigInteger userId,
                                @RequestParam(required = false)String enrollmentTime,
                                @RequestParam(required = false)String realName){

        try {
            teacherService.editTeacher(id,userId,enrollmentTime,realName);
            return "修改成功";
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

    @RequestMapping("/teacher/insert")
    public String teacherInsert(@RequestParam(name = "id")BigInteger id,
                                @RequestParam(name = "userId")BigInteger userId,
                                @RequestParam(required = false)String enrollmentTime,
                                @RequestParam(required = false)String realName
                                ){

         try{
             teacherService.editTeacher(id,userId,enrollmentTime,realName);
             return "新增教师成功";
         }catch (Exception e){
             return e.getMessage();
         }
    }
    @RequestMapping("/teacher/delete")
    public String teacherDelete(@RequestParam(name="id") BigInteger id
                                ){
         try {
             teacherService.delete(id);
             return "删除或者恢复成功";
         }catch (Exception e){
             return e.getMessage();
         }
    }
}
