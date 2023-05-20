package com.qc.module.appIndexTagIdRelation.service;

import com.qc.module.appIndexTagIdRelation.mapper.AppIndexTagIdRelationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 展示标签关系表 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-05-20
 */
@Service
 public class AppIndexTagIdRelationService {

    @Resource
    AppIndexTagIdRelationMapper mapper;

    public String getTagIdByShowTagId(Integer showTagId) {
        List<BigInteger> tagIdByShowTagId = mapper.getTagIdByShowTagId(showTagId);
        StringBuilder ss = new StringBuilder();
        for(BigInteger t:tagIdByShowTagId){
            ss.append(t+",");
        }
        int len = ss.length();
        if(len==0){
            return null;
        }
        ss.delete(len-1,len);
        return ss.toString();
    }
}
