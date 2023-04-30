package com.qc.module.course.service;

import com.qc.module.course.entity.CourseTag;
import com.qc.module.course.mapper.CourseTagMapper;
import com.qc.utils.BaseUtils;
import javassist.util.HotSwapAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CourseTagService {

    @Resource
    private CourseTagMapper mapper;

    public List<CourseTag> getTagList(Integer pageNum,Integer pageSize){
        Integer begin = (pageNum-1)*pageSize;
        return mapper.getTagList(begin,pageSize);
    }
    @Transactional(rollbackFor = Exception.class)
    public List<BigInteger> edit(String tags) {
        if (!BaseUtils.isEmpty(tags)) {
            List<String> tagList = Arrays.asList(tags.split("\\$"));
            int len = tagList.size();
            Integer now = BaseUtils.currentSeconds();
            List list = new ArrayList<>();
            if (len != 0) {
                for (String tag : tagList) {
                    BigInteger id = mapper.extractIdByTag(tag);
                    CourseTag courseTag = new CourseTag();
                    if (!BaseUtils.isEmpty(id)) {
                        list.add(id);
                    } else {
                        courseTag.setTag(tag);
                        courseTag.setCreateTime(now);
                        mapper.insert(courseTag);
                        list.add(courseTag.getId());
                    }
                }
            }
            return list;
        }
        return null;
    }

    public BigInteger getIdByTag(String tag){
        return mapper.getId(tag);
    }
    public String getIdsByTag(String tags){

        List<BigInteger> idsByTag = mapper.getIdsByTag(tags);
        StringBuilder ss = new StringBuilder();
        for(BigInteger i:idsByTag){
            ss.append(i+",");
        }
        int len = ss.length();
        if(len==0){
            return null;
        }
        ss.delete(len-1,len);
        return ss.toString();
    }

    public BigInteger insertTag(String tag){
        CourseTag courseTag = new CourseTag();
        courseTag.setTag(tag);
        mapper.insert(courseTag);
        return courseTag.getId();
    }

    public List<String> getTagsByTagIds(String tagIds){
        return mapper.getTags(tagIds);
    }

    public BigInteger deleteByTag(String tag){
        int updateTime = BaseUtils.currentSeconds();
        int result = mapper.deleteByTag(tag,updateTime);
        if(result==0){
            throw new RuntimeException("delete failed!");
        }
        BigInteger idByTag = mapper.extractIdByTag(tag);
        return idByTag;
    }

    public Integer getTotal(){
        return mapper.getTotal();
    }
}
