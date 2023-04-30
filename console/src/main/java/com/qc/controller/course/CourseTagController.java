package com.qc.controller.course;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.tag.TagInfoVo;
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

import java.util.ArrayList;
import java.util.List;


@RestController
public class CourseTagController {
    @Autowired
    private CourseTagService courseTagService;
    @Autowired
    private BaseCourseTagService baseCourseTagService;

    @RequestMapping("/tag/delete")
    public Response delete(@VerifiedUser User loginUser,
                           @RequestParam(required = false,name = "tag") String tag){

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        try {
            baseCourseTagService.deleteByTag(tag);
            return new Response(1001);
        }catch (Exception e){
            return new Response(4004);
        }
    }

    @RequestMapping("/tag/list")
    public Response tagList(@VerifiedUser User loginUser,
                            @RequestParam(required = false,name="pageNum") Integer inputPageNum){

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
        List<CourseTag> courseTags = courseTagService.getTagList(pageNum,pageSize);
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
        baseListVo.setTagTotal(courseTagService.getTotal());
        baseListVo.setTagList(tagInfoVoList);
        return new Response(1001, baseListVo);
    }
}
