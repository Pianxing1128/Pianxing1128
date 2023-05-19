package com.qc.module.appIndexBanner.service;

import com.qc.module.appIndexBanner.entity.AppIndexBanner;
import com.qc.module.appIndexBanner.mapper.AppIndexBannerMapper;
import com.qc.utils.BaseUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Service
public class AppIndexBannerService {

    @Resource
    private AppIndexBannerMapper mapper;

    public List<AppIndexBanner> getIndexBannerListForApp(){
        return mapper.getIndexBannerList();
    }

    public List<AppIndexBanner> extractIndexBannerListForConsole(Integer pageNum,Integer pageSize,String bannerName){
        int begin = (pageNum-1)*pageSize;
        return mapper.extractIndexBannerList(begin,pageSize,bannerName);
    }

    public Integer extractTotal(){
        return mapper.extractTotal();
    }

    @Transactional(rollbackFor = Exception.class)
    public BigInteger edit(BigInteger id,String bannerName, String bannerImage, String bannerLink) {

        AppIndexBanner appIndexBanner = new AppIndexBanner();
        appIndexBanner.setId(id);
        appIndexBanner.setBannerName(bannerName);
        appIndexBanner.setBannerImage(bannerImage);
        appIndexBanner.setBannerLink(bannerLink);

        if(!BaseUtils.isEmpty(id)){
            AppIndexBanner oldAppIndexBanner = mapper.extractById(id);
            if(BaseUtils.isEmpty(oldAppIndexBanner)){
                throw new RuntimeException("This appIndexBanner does not exist!");
            }
            int update = mapper.update(appIndexBanner);
            if(update==0){
                throw new RuntimeException("Update Failed!");
            }
            return id;
        }
        appIndexBanner.setCreateTime(BaseUtils.currentSeconds());
        mapper.insert(appIndexBanner);
        return appIndexBanner.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(BigInteger id) {
        AppIndexBanner oldAppIndexBanner = mapper.extractById(id);
        if(BaseUtils.isEmpty(oldAppIndexBanner)){
            throw new RuntimeException("This oldAppIndexBanner does not exist!");
        }
        int updateTime = BaseUtils.currentSeconds();
        mapper.delete(id,updateTime);
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateAll(String ids){
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
