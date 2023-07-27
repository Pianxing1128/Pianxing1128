package com.qc.controller.teacher;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.teacher.TeacherVo;
import com.qc.module.teacher.entity.Teacher;
import com.qc.module.teacher.service.TeacherService;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.BaseUtils;
import com.qc.utils.Response;
import com.qc.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qc112
 */
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @RequestMapping("/teacher/list")
    public Response teacherList(@VerifiedUser User loginUser,
                                @RequestParam(required = false,name = "pageNum") Integer inputPageNum,
                                @RequestParam(required = false,name = "realName")String realName){

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
        BaseListVo baseListVo = new BaseListVo();
        List<Teacher> teacherList = teacherService.extractTeacherList(pageNum,pageSize,realName);
        List<TeacherVo> list = new ArrayList<>();

        for(Teacher t:teacherList){
            TeacherVo teacherVo = new TeacherVo();

            User user = userService.getById(t.getUserId());
            if(BaseUtils.isEmpty(user)){
                continue;
            }
            teacherVo.setId(t.getId());
            teacherVo.setUserId(t.getUserId());
            teacherVo.setEnrollmentTime(t.getEnrollmentTime());
            teacherVo.setRealName(t.getRealName());
            teacherVo.setUpdateTime(BaseUtils.timeStamp2Date(t.getUpdateTime()));
            teacherVo.setCreateTime(BaseUtils.timeStamp2Date(t.getCreateTime()));
            teacherVo.setIsDeleted(t.getIsDeleted());
            list.add(teacherVo);
        }
        baseListVo.setTeacherTotal(teacherService.getTotal());
        baseListVo.setPageSize(pageSize);
        baseListVo.setTeacherList(list);

        return new Response(1001,baseListVo);
    }
    @RequestMapping("/teacher/insert")
    public Response teacherInsert(@VerifiedUser User loginUser,
                                  @RequestParam(required = false,name = "id")BigInteger id,
                                  @RequestParam(name = "userId")BigInteger userId,
                                  @RequestParam(name = "enrollmentTime")String enrollmentTime,
                                  @RequestParam(name = "realName")String realName){
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        User user = userService.getById(userId);
        if(BaseUtils.isEmpty(user)){
            return new Response(3052); //老师ID不正确
        }
        try{
            teacherService.edit(id,userId,enrollmentTime,realName);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }
    @RequestMapping("/teacher/update")
    public Response teacherUpdate(@VerifiedUser User loginUser,
                                  @RequestParam(name="id")BigInteger id,
                                  @RequestParam(required = false,name = "userId")BigInteger userId,
                                  @RequestParam(required = false,name = "enrollmentTime")String enrollmentTime,
                                  @RequestParam(required = false,name = "realName")String realName){

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }

        if(!BaseUtils.isEmpty(userId)) {
            User user = userService.getById(userId);
            if (BaseUtils.isEmpty(user)) {
                return new Response(3052); //老师ID不正确
            }
        }

        try{
            teacherService.edit(id,userId,enrollmentTime,realName);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }


    @RequestMapping("/teacher/delete")
    public Response teacherDelete(@VerifiedUser User loginUser,
                                  @RequestParam(name="id")BigInteger id){

         if (BaseUtils.isEmpty(loginUser)) {
             return new Response(1002);
         }

         try {
             teacherService.delete(id);
             return new Response(1001);
         }catch (Exception e){
             return new Response(4004);
         }
    }
}
