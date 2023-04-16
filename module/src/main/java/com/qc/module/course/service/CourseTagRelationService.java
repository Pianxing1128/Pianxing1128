package com.qc.module.course.service;

import com.qc.module.course.entity.CourseTagRelation;
import com.qc.module.course.mapper.CourseTagRelationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class CourseTagRelationService {

    @Resource
    private CourseTagRelationMapper mapper;
    @Transactional(rollbackFor = Exception.class)
    public BigInteger insert(BigInteger courseId,BigInteger tagId ){
        try {
            CourseTagRelation courseTagRelation = new CourseTagRelation();
            courseTagRelation.setCourseId(courseId);
            courseTagRelation.setTagId(tagId);
            mapper.insert(courseTagRelation);
            return courseTagRelation.getId();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
    }

    public String getTagIds(BigInteger id){
        BigInteger courseId = id;
        List<String> tagIdList = mapper.getTagIds(courseId);
        StringBuilder ss = new StringBuilder();
        for(String t:tagIdList){
            ss.append(t+",");
        }
        if(ss.length()==0){
            return "-1";
        }
        int len = ss.length();
        ss.delete(len-1,len);
        return ss.toString();
    }

    public int delete(BigInteger idByTag){
        return  mapper.delete(idByTag);
    }

    public String getCourseIds(String idsByTag){

        List<String> courseIdList = mapper.getCourseIds(idsByTag);
        StringBuilder ss = new StringBuilder();
        for(String c:courseIdList){
            ss.append(c+",");
        }
        if(ss.length()==0){
            return "-1";
        }
        int len = ss.length();
        ss.delete(len-1,len);
        return ss.toString();
    }
}
