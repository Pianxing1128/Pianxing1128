package com.qc.controller.appIndexBanner;

import com.qc.domain.BaseListVo;
import com.qc.domain.appIndexBanner.AppIndexBannerVo;
import com.qc.module.appIndexBanner.entity.AppIndexBanner;
import com.qc.module.appIndexBanner.service.AppIndexBannerService;
import com.qc.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppIndexBannerController {

    @Autowired
    private AppIndexBannerService appIndexBannerService;

    @RequestMapping("/index/banner/list")
    public Response indexBannerList(){

        List<AppIndexBanner> indexBannerList = appIndexBannerService.getIndexBannerListForApp();
        List<AppIndexBannerVo> list = new ArrayList<>();
        for(AppIndexBanner i:indexBannerList){
            AppIndexBannerVo appIndexBannerVo = new AppIndexBannerVo();
            appIndexBannerVo.setBannerImage(i.getBannerImage());
            appIndexBannerVo.setBannerLink(i.getBannerLink());
            list.add(appIndexBannerVo);
        }
        BaseListVo baseListVo = new BaseListVo();
        baseListVo.setIndexBannerList(list);
        return new Response(1001,baseListVo);
    }
}
