package com.qc.controller.course;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.courseTagRelation.CourseTagRelationVo;
import com.qc.module.course.entity.CourseTagRelation;
import com.qc.module.course.service.CourseTagRelationService;
import com.qc.module.user.entity.User;
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

@RestController
public class CourseTagRelationController {

    @Autowired
    private CourseTagRelationService courseTagRelationService;

    @RequestMapping("/course/tag/relation/list")
    public Response courseTagRelationList(@VerifiedUser User loginUser,
                                          @RequestParam(required = false,name = "pageNum") Integer inputPageNum,
                                          @RequestParam(required = false,name = "courseId")BigInteger courseId,
                                          @RequestParam(required = false,name = "tagId")BigInteger tagId){

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
        List<CourseTagRelation> courseTagRelationList = courseTagRelationService.extractCourseTagRelationList(pageNum,pageSize,courseId,tagId);

        BaseListVo baseListVo = new BaseListVo();
        List<CourseTagRelationVo> list = new ArrayList<>();
        for(CourseTagRelation c:courseTagRelationList){

            CourseTagRelationVo courseTagRelationVo = new CourseTagRelationVo();
            courseTagRelationVo.setId(c.getId());
            courseTagRelationVo.setCourseId(c.getCourseId());
            courseTagRelationVo.setTagId(c.getTagId());
            courseTagRelationVo.setUpdateTime(BaseUtils.timeStamp2Date(c.getUpdateTime()));
            courseTagRelationVo.setCreateTime(BaseUtils.timeStamp2Date(c.getCreateTime()));
            courseTagRelationVo.setIsDeleted(c.getIsDeleted());
            list.add(courseTagRelationVo);
        }
        baseListVo.setPageSize(pageSize);
        baseListVo.setCourseTagTotal(courseTagRelationService.extractTotal());
        baseListVo.setCourseTagRelationList(list);

        return new Response(1001,baseListVo);
    }

    @RequestMapping("/course/tag/relation/insert")
    public Response courseTagRelationInsert(@VerifiedUser User loginUser,
                                    @RequestParam(required = false,name = "id") BigInteger id,
                                    @RequestParam(name = "courseId")BigInteger courseId,
                                    @RequestParam(name = "tagId")BigInteger tagId){


        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }

        try {
            courseTagRelationService.edit(id,courseId,tagId);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }

    @RequestMapping("/course/tag/relation/update")
    public Response courseTagRelationUpdate(@VerifiedUser User loginUser,
                                    @RequestParam(name = "id") BigInteger id,
                                    @RequestParam(required = false,name = "courseId")BigInteger courseId,
                                    @RequestParam(required = false,name = "tagId")BigInteger tagId){


        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }

        try {
            courseTagRelationService.edit(id,courseId,tagId);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }

    @RequestMapping("/course/tag/relation/delete")
    public Response courseTagRelationDelete(@VerifiedUser User loginUser,
                                            @RequestParam(name = "id") BigInteger id){
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        try {
            courseTagRelationService.delete(id);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }

    }
}
