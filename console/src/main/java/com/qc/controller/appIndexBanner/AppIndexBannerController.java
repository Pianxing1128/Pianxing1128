package com.qc.controller.appIndexBanner;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.appIndexBanner.AppIndexBannerVo;
import com.qc.module.appIndexBanner.entity.AppIndexBanner;
import com.qc.module.appIndexBanner.service.AppIndexBannerService;
import com.qc.module.user.entity.User;
import com.qc.utils.BaseUtils;
import com.qc.utils.Response;
import com.qc.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AppIndexBannerController {

    @Autowired
    private AppIndexBannerService appIndexBannerService;

    @RequestMapping("/index/banner/list")
    public Response indexBannerList(@VerifiedUser User loginUser,
                                    @RequestParam(required = false,name="pageNum")Integer inputPageNum,
                                    @RequestParam(required = false,name="bannerName")String bannerName){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        Integer pageNum;
        if(inputPageNum==null || inputPageNum<=0){
            pageNum=1;
        }else {
            pageNum = inputPageNum;
        }
        Integer pageSize = Integer.valueOf(SpringUtils.getProperty("application.pagesize"));

        BaseListVo baseListVo = new BaseListVo();

        List<AppIndexBanner> appIndexBannerList = appIndexBannerService.extractIndexBannerListForConsole(pageNum,pageSize,bannerName);
        List<AppIndexBannerVo> list = new ArrayList<>();
        for(AppIndexBanner i:appIndexBannerList){
            AppIndexBannerVo appIndexBannerVo = new AppIndexBannerVo();
            appIndexBannerVo.setId(i.getId());
            appIndexBannerVo.setBannerName(i.getBannerName());
            appIndexBannerVo.setBannerImage(i.getBannerImage());
            appIndexBannerVo.setBannerLink(i.getBannerLink());
            appIndexBannerVo.setUpdateTime(BaseUtils.timeStamp2Date(i.getUpdateTime()));
            appIndexBannerVo.setCreateTime(BaseUtils.timeStamp2Date(i.getCreateTime()));
            appIndexBannerVo.setIsDeleted(i.getIsDeleted());
            list.add(appIndexBannerVo);
        }

        baseListVo.setPageSize(pageSize);
        baseListVo.setAppIndexBannerTotal(appIndexBannerService.extractTotal());
        baseListVo.setAppIndexBannerList(list);

        return new Response(1001,baseListVo);
    }

    @RequestMapping("/index/banner/insert")
    public Response indexBannerInsert(@VerifiedUser User loginUser,
                                      @RequestParam(required = false,name = "id")BigInteger id,
                                      @RequestParam(name = "bannerName")String bannerName,
                                      @RequestParam(name = "bannerImage")String bannerImage,
                                      @RequestParam(name = "bannerLink")String bannerLink){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        bannerName = bannerName.trim();
        bannerImage = bannerImage.trim();
        bannerLink = bannerLink.trim();

        if(BaseUtils.isEmpty(bannerName)||BaseUtils.isEmpty(bannerImage)||BaseUtils.isEmpty(bannerLink)){
            return new Response(3051); //必填信息不能为空
        }

        try {
            BigInteger bannerId = appIndexBannerService.edit(id, bannerName, bannerImage, bannerLink);
            return new Response(1001, bannerId);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/index/banner/update")
    public Response indexBannerUpdate(@VerifiedUser User loginUser,
                                      @RequestParam(name = "id")BigInteger id,
                                      @RequestParam(required = false,name = "bannerName")String bannerName,
                                      @RequestParam(required = false,name = "bannerImage")String bannerImage,
                                      @RequestParam(required = false,name = "bannerLink")String bannerLink){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        try {
            BigInteger bannerId = appIndexBannerService.edit(id, bannerName, bannerImage, bannerLink);
            return new Response(1001, bannerId);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/index/banner/delete")
    public Response indexBannerDelete(@VerifiedUser User loginUser,
                                      @RequestParam(name="id")BigInteger id){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        try {
            appIndexBannerService.delete(id);
            return new Response(1001);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/index/banner/update/all")
    public Response indexBannerUpdateAll(@VerifiedUser User loginUser,
                                         @RequestParam(name = "ids")String ids){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002); //需要登陆操作
        }

        ids = ids.trim();
        if(BaseUtils.isEmpty(ids)){
            return new Response(3052); //必填信息不能为空
        }
        try {
            String bannerShowIdList = appIndexBannerService.updateAll(ids);
            return new Response(1001, bannerShowIdList);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }
}
