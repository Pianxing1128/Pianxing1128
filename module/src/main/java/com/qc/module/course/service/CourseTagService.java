package com.qc.module.course.service;


import com.qc.module.course.entity.CourseTag;
import com.qc.module.course.entity.CourseTagRelation;
import com.qc.module.course.mapper.CourseTagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class CourseTagService {

    @Resource
    private CourseTagMapper mapper;
    @Resource
    private CourseTagRelationService courseTagRelationService;

    @Transactional(rollbackFor=Exception.class)
    public String insertTag(String tag,BigInteger courseId){
        try {
            BigInteger tagId = mapper.getId(tag);
            if(tagId==null){
                CourseTag courseTag = new CourseTag();
                courseTag.setTag(tag);
                mapper.insert(courseTag);
                tagId = courseTag.getId();
            }

            BigInteger relationId = courseTagRelationService.insert(courseId,tagId);
            String result = "新增成功结果为---courseId:"+ courseId + ",tagId:"+tagId + ",relationId:" + relationId;
            return result;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "新增到关系表失败";
        }
    }

    public BigInteger getIdByTag(String tag){
        return mapper.getId(tag);
    }
    public String getIdsByTag(String tag){
        List<String> idsByTag = mapper.getIdsByTag(tag);
        StringBuilder ss = new StringBuilder();
        for(String i:idsByTag){
            ss.append(i+",");
        }
        int len = ss.length();
        if(len==0){
            return "-1";
        }
        ss.delete(len-1,len);
        return ss.toString();
    }

    public BigInteger insert(String tag){
        CourseTag courseTag = new CourseTag();
        courseTag.setTag(tag);
        mapper.insert(courseTag);
        return courseTag.getId();
    }

    public List<String> getTag(String tagIds){
        return mapper.getTag(tagIds);
    }

    public int delete(String tag){
        return mapper.delete(tag);
    }

}
