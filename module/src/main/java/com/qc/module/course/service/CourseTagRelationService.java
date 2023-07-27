package com.qc.module.course.service;

import com.qc.module.course.entity.CourseTagRelation;
import com.qc.module.course.mapper.CourseTagRelationMapper;
import com.qc.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseTagRelationService {

    @Resource
    private CourseTagRelationMapper mapper;

    private CourseService courseService;
    @Autowired
    public CourseTagRelationService(CourseService courseService){
        this.courseService = courseService;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<BigInteger> editCourseTagRelation(BigInteger courseId,List<BigInteger> tagsList ) { //(100, (1,2,40))

        int now = BaseUtils.currentSeconds();

        if (BaseUtils.isEmpty(courseId)) {
            throw new RuntimeException("This course does not exist!");
        }

        List<BigInteger> oldRelationIdList = extractIdByCourseId(courseId);
        if (!BaseUtils.isEmpty(oldRelationIdList)) { //如果courseId可以查到 relation存在
            mapper.deleteByCourseId(courseId, now);// is_deleted 全部变成1   --- 执行1次
        }
        List list = new ArrayList<>();
        if (!BaseUtils.isEmpty(tagsList)) {
            for (BigInteger tagId : tagsList) {  //深圳英语$四川英语

                BigInteger newRelationId = mapper.extractIdByCourseIdAndTagId(courseId, tagId);//  --- 执行n次
                if (!BaseUtils.isEmpty(newRelationId)) {
                    mapper.recover(newRelationId, now);// is_deleted 从1 变成 0  ---- 执行n次
                    list.add(newRelationId);
                } else {
                    CourseTagRelation courseTagRelation = new CourseTagRelation();
                    courseTagRelation.setCourseId(courseId);
                    courseTagRelation.setTagId(tagId);
                    courseTagRelation.setCreateTime(now);
                    mapper.insert(courseTagRelation);  //如果新增失败就会自己抛出异常，被exception.class 捕获
                    list.add(courseTagRelation.getId());
                }
            }
            return list;
        }
        return oldRelationIdList;
    }

    public String getTagIds(BigInteger id){
        BigInteger courseId = id;
        List<BigInteger> tagIdList = mapper.getTagIds(courseId);
        StringBuilder ss = new StringBuilder();
        for(BigInteger t:tagIdList){
            ss.append(t+",");
        }
        if(ss.length()==0){
            return "-1";
        }
        int len = ss.length();
        ss.delete(len-1,len);
        return ss.toString();
    }



    @Transactional(rollbackFor = Exception.class)
    public void deleteByCourseId(BigInteger courseId){
        int updateTime = BaseUtils.currentSeconds();
        mapper.deleteByCourseId(courseId,updateTime);
    }

    public String getCourseIds(String idsByTag){

        List<String> courseIdList = mapper.getCourseIds(idsByTag);
        StringBuilder ss = new StringBuilder();
        for(String c:courseIdList){
            ss.append(c+",");
        }
        if(ss.length()==0){
            return null;
        }
        int len = ss.length();
        ss.delete(len-1,len);
        return ss.toString();
    }

    public List<BigInteger> extractIdByCourseId(BigInteger courseId){
        List<BigInteger> relationIds = mapper.extractIdByCourseId(courseId);
        return relationIds;
    }

    @Transactional(rollbackFor = Exception.class)
    //这里要加事务，因为有可能关系表 1个tagId对应的2个Relation，1个已经被删除，再update时候 返回结果为0,另外1个是1，没办法判断返回结果0和1
    public void deleteByTagId(BigInteger tagId){
        int updateTime = BaseUtils.currentSeconds();
        mapper.deleteByTagId(tagId,updateTime);
    }

    public void recoverByTag(BigInteger tagId) {
        int updateTime = BaseUtils.currentSeconds();
        mapper.recoverByTagId(tagId,updateTime);
    }

    public List<CourseTagRelation> extractCourseTagRelationList(Integer pageNum, Integer pageSize, BigInteger courseId, BigInteger tagId) {
        int begin = (pageNum-1)*pageSize;
        return mapper.extractCourseTagRelationList(begin,pageSize,courseId,tagId);
    }

    public Integer extractTotal() {
        return mapper.extractTotal();
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger edit(BigInteger id, BigInteger courseId, BigInteger tagId) {
        CourseTagRelation courseTagRelation = new CourseTagRelation();
        courseTagRelation.setId(id);
        courseTagRelation.setCourseId(courseId);
        courseTagRelation.setTagId(tagId);

        if(!BaseUtils.isEmpty(id)){
            CourseTagRelation oldCourseTagRelation = mapper.extractById(id);
            if(BaseUtils.isEmpty(oldCourseTagRelation)){
                throw new RuntimeException("This courseTagRelation doest not exist!");
            }
            int update = mapper.update(courseTagRelation);
            if(update==0){
                throw new RuntimeException("Update Failed!");
            }
            return id;
        }

        int now = BaseUtils.currentSeconds();
        courseTagRelation.setCreateTime(now);
        mapper.insert(courseTagRelation);
        return courseTagRelation.getId();
    }

    public void delete(BigInteger id) {
        int updateTime = BaseUtils.currentSeconds();
        mapper.delete(id, updateTime);
    }
}
