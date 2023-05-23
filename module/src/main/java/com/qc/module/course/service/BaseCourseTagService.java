package com.qc.module.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;

@Service
public class BaseCourseTagService {

    private CourseTagService courseTagService;
    private CourseTagRelationService courseTagRelationService;
    @Autowired
    public BaseCourseTagService(CourseTagService courseTagService,CourseTagRelationService courseTagRelationService){
        this.courseTagService=courseTagService;
        this.courseTagRelationService=courseTagRelationService;
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTag(String tag){
        BigInteger tagId = courseTagService.deleteByTag(tag);
        courseTagRelationService.deleteByTagId(tagId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByTagId(BigInteger id) {
        courseTagService.delete(id);
        courseTagRelationService.deleteByTagId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void recoverByTagId(BigInteger id) {
        courseTagService.recover(id);
        courseTagRelationService.recoverByTag(id);
    }
}
