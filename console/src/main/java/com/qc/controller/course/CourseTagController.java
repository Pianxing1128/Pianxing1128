package com.qc.controller.course;

import com.qc.annotations.VerifiedUser;
import com.qc.module.course.service.CourseTagRelationService;
import com.qc.module.course.service.CourseTagService;
import com.qc.module.user.entity.User;
import com.qc.utils.BaseUtils;
import com.qc.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;


@RestController
@Slf4j
public class CourseTagController {

    @Autowired
    private CourseTagService courseTagService;

    @Autowired
    private CourseTagRelationService courseTagRelationService;

    @RequestMapping("/tag/delete")
    @Transactional(rollbackFor = Exception.class)
    public Response delete(@VerifiedUser User loginUser,
                           @RequestParam(required = false,name = "tag") String tag){

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1002);
        }
        try {
            int courseTagDelete = courseTagService.delete(tag);
            BigInteger idByTag = courseTagService.getIdByTag(tag);
            int RelationDelete = courseTagRelationService.delete(idByTag);
            String result = "course_tag表里的tag删除成功个数为："+ courseTagDelete + "，Relation表的关联删除成功个数为：" + RelationDelete;
            return new Response(1001,result);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Response(4004);
        }

    }
}
