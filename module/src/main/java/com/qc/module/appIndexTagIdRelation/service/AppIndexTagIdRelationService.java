package com.qc.module.appIndexTagIdRelation.service;

import com.qc.module.appIndexTagIdRelation.entity.AppIndexTagIdRelation;
import com.qc.module.appIndexTagIdRelation.mapper.AppIndexTagIdRelationMapper;
import com.qc.utils.BaseUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private AppIndexTagIdRelationMapper mapper;

    public String getTagIdsByShowTagId(Integer showTagId) {
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

    public List<AppIndexTagIdRelation> extractIndexTagIdRelationListForConsole(Integer pageNum, Integer pageSize, Integer showTagId, Integer tagId) {
        int begin = (pageNum-1)*pageSize;
        return mapper.extractIndexTagIdRelationListForConsole(begin,pageSize,showTagId,tagId);

    }

    public Integer extractTotal() {
        return mapper.extractTotal();
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger edit(BigInteger id, BigInteger showTagId, BigInteger tagId) {
        AppIndexTagIdRelation appIndexTagIdRelation = new AppIndexTagIdRelation();
        appIndexTagIdRelation.setId(id);
        appIndexTagIdRelation.setShowTagId(showTagId);
        appIndexTagIdRelation.setTagId(tagId);

        if(!BaseUtils.isEmpty(id)){
            AppIndexTagIdRelation oldAppIndexTagIdRelation = mapper.extractById(id);
            if(BaseUtils.isEmpty(oldAppIndexTagIdRelation)){
                throw new RuntimeException("This appIndexTagIdRelation does not exist!");
            }
            int update = mapper.update(oldAppIndexTagIdRelation);
            if(update==0){
                throw new RuntimeException("Update Failed!");
            }
            return id;
        }
        appIndexTagIdRelation.setCreateTime(BaseUtils.currentSeconds());
        mapper.insert(appIndexTagIdRelation);
        return appIndexTagIdRelation.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(BigInteger id) {
        int updateTime = BaseUtils.currentSeconds();
        return mapper.delete(id,updateTime);
    }
}
