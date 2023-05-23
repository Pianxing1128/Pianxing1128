package com.qc.controller.course;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.courseTag.TagInfoVo;
import com.qc.module.course.entity.CourseTag;
import com.qc.module.course.service.BaseCourseTagService;
import com.qc.module.course.service.CourseTagService;
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
public class CourseTagController {

    @Autowired
    private CourseTagService courseTagService;
    @Autowired
    private BaseCourseTagService baseCourseTagService;


    @RequestMapping("/course/tag/list")
    public Response tagList(@VerifiedUser User loginUser,
                            @RequestParam(required = false,name="pageNum") Integer inputPageNum,
                            @RequestParam(required = false,name="tag")String tag){

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
        List<CourseTag> courseTags = courseTagService.extractCourseTagList(pageNum,pageSize,tag);
        BaseListVo baseListVo = new BaseListVo();
        List<TagInfoVo> tagInfoVoList = new ArrayList<>();

        for(CourseTag c:courseTags){
            TagInfoVo tagInfoVo = new TagInfoVo();
            tagInfoVo.setId(c.getId());
            tagInfoVo.setTag(c.getTag());
            tagInfoVo.setUpdateTime(BaseUtils.timeStamp2Date(c.getUpdateTime()));
            tagInfoVo.setCreateTime(BaseUtils.timeStamp2Date(c.getCreateTime()));
            tagInfoVo.setIsDeleted(c.getIsDeleted());
            tagInfoVoList.add(tagInfoVo);
        }
        baseListVo.setPageSize(pageSize);
        baseListVo.setCourseTagTotal(courseTagService.getTotal());
        baseListVo.setCourseTagList(tagInfoVoList);
        return new Response(1001, baseListVo);
    }

    @RequestMapping("/course/tag/insert")
    public Response courseTagInsert(@VerifiedUser User loginUser,
                                    @RequestParam(required = false,name = "id") BigInteger id,
                                    @RequestParam(name = "tag")String tag){


         if (BaseUtils.isEmpty(loginUser)) {
             return new Response(1002);
         }
        try {
            courseTagService.edit(id,tag);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }

    @RequestMapping("/course/tag/update")
    public Response courseTagUpdate(@VerifiedUser User loginUser,
                                    @RequestParam(required = false,name = "id") BigInteger id,
                                    @RequestParam(name = "tag")String tag){


         if (BaseUtils.isEmpty(loginUser)) {
             return new Response(1002);
         }
        try {
            courseTagService.edit(id,tag);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }

    @RequestMapping("/course/tag/delete")
    public Response courseTagDelete(@VerifiedUser User loginUser,
                           @RequestParam(name = "id")BigInteger id){

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        try {
            baseCourseTagService.deleteByTagId(id);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }

    @RequestMapping("/course/tag/recover")
    public Response courseTagRecover(@VerifiedUser User loginUser,
                           @RequestParam(name = "id")BigInteger id){

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        try {
            baseCourseTagService.recoverByTagId(id);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }

}
