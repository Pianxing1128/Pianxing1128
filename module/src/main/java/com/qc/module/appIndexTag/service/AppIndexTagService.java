package com.qc.module.appIndexTag.service;

import com.qc.module.appIndexTag.entity.AppIndexTag;
import com.qc.module.appIndexTag.mapper.AppIndexTagMapper;
import com.qc.utils.BaseUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 标签展示 服务实现类
 * </p>
 *
 * @author qc1128
 * @since 2023-05-19
 */
@Service
public class AppIndexTagService  {

    @Resource
    private AppIndexTagMapper mapper;

    public List<AppIndexTag> getAppIndexTagListForApp() {
        return mapper.getAppIndexTagListForApp();
    }

    public List<AppIndexTag> extractIndexTagListForConsole(Integer pageNum, Integer pageSize, String showTagName) {
        int begin = (pageNum-1)*pageSize;
        return mapper.extractAppIndexTagListForConsole(begin,pageSize,showTagName);
    }

    public Integer extractTotal() {
        return mapper.extractTotal();
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger edit(BigInteger id, String showTagName) {
        AppIndexTag appIndexTag = new AppIndexTag();
        appIndexTag.setId(id);
        appIndexTag.setShowTagName(showTagName);

        if(!BaseUtils.isEmpty(id)){
            AppIndexTag oldAppIndexTag = mapper.extractById(id);
            if(BaseUtils.isEmpty(oldAppIndexTag)){
                throw new RuntimeException("This appIndexTag does not exist!");
            }
            int update = mapper.update(appIndexTag);
            if(update==0){
                throw new RuntimeException("Update Failed!");
            }
            return id;
        }
        appIndexTag.setCreateTime(BaseUtils.currentSeconds());
        mapper.insert(appIndexTag);
        return appIndexTag.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(BigInteger id) {
        int updateTime = BaseUtils.currentSeconds();
        mapper.delete(id,updateTime);
    }

    public String updateAll(String ids) {
        List<String> idList = Arrays.asList(ids.split("\\$"));
        StringBuilder ss = new StringBuilder();
        for(String x:idList){
            ss.append(x+",");
        }
        int len  = ss.length();
        if(len==0){
            return null;
        }
        ss.delete(len-1,len);
        int updateTime = BaseUtils.currentSeconds();
        mapper.deleteAll(updateTime);
        String newIdList = ss.toString();
        mapper.recover(newIdList,updateTime);
        return newIdList;
    }
}
