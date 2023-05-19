package com.qc.module.appIndexBanner.service;

import com.qc.module.appIndexBanner.entity.AppIndexBanner;
import com.qc.module.appIndexBanner.mapper.AppIndexBannerMapper;
import com.qc.utils.BaseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
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

    public int delete(BigInteger id) {
        AppIndexBanner oldAppIndexBanner = mapper.extractById(id);
        if(BaseUtils.isEmpty(oldAppIndexBanner)){
            throw new RuntimeException("This oldAppIndexBanner does not exist!");
        }
        Integer updateTime = BaseUtils.currentSeconds();
        return mapper.delete(id,updateTime);
    }
}
