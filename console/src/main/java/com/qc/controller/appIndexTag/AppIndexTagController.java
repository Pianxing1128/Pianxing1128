package com.qc.controller.appIndexTag;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.appIndexTag.AppIndexTagVo;
import com.qc.module.appIndexTag.entity.AppIndexTag;
import com.qc.module.appIndexTag.service.AppIndexTagService;
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
public class AppIndexTagController {

    @Autowired
    private AppIndexTagService appIndexTagService;

    @RequestMapping("/index/tag/list")
    public Response indexTagList(@VerifiedUser User loginUser,
                                @RequestParam(required = false,name="pageNum")Integer inputPageNum,
                                @RequestParam(required = false,name="showTagName")String showTagName){

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

        List<AppIndexTag> appIndexTagList = appIndexTagService.extractIndexTagListForConsole(pageNum,pageSize,showTagName);
        List<AppIndexTagVo> list = new ArrayList<>();
        for(AppIndexTag i:appIndexTagList){
            AppIndexTagVo appIndexTagVo = new AppIndexTagVo();
            appIndexTagVo.setId(i.getId());
            appIndexTagVo.setShowTagName(i.getShowTagName());
            appIndexTagVo.setUpdateTime(BaseUtils.timeStamp2Date(i.getUpdateTime()));
            appIndexTagVo.setCreateTime(BaseUtils.timeStamp2Date(i.getCreateTime()));
            appIndexTagVo.setIsDeleted(i.getIsDeleted());
            list.add(appIndexTagVo);
        }

        baseListVo.setPageSize(pageSize);
        baseListVo.setAppIndexBannerTotal(appIndexTagService.extractTotal());
        baseListVo.setAppIndexBannerList(list);

        return new Response(1001,baseListVo);
    }

    @RequestMapping("/index/tag/insert")
    public Response indexTagInsert(@VerifiedUser User loginUser,
                                  @RequestParam(required = false,name = "id") BigInteger id,
                                  @RequestParam(name = "showTagName")String showTagName){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        showTagName = showTagName.trim();

        if(BaseUtils.isEmpty(showTagName)){
            return new Response(3051); //必填信息不能为空
        }

        try {
            BigInteger bannerId = appIndexTagService.edit(id,showTagName);
            return new Response(1001, bannerId);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/index/tag/update")
    public Response indexTagUpdate(@VerifiedUser User loginUser,
                                  @RequestParam(name = "id")BigInteger id,
                                  @RequestParam(required = false,name = "showTagName")String showTagName){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        try {
            BigInteger bannerId = appIndexTagService.edit(id,showTagName);
            return new Response(1001, bannerId);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/index/tag/delete")
    public Response indexTagDelete(@VerifiedUser User loginUser,
                                  @RequestParam(name="id")BigInteger id){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        try {
            appIndexTagService.delete(id);
            return new Response(1001);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/index/tag/update/all")
    public Response indexTagUpdateAll(@VerifiedUser User loginUser,
                                     @RequestParam(name = "ids")String ids){

//        if(BaseUtils.isEmpty(loginUser)){
//            return new Response(1002); //需要登陆操作
//        }

        ids = ids.trim();
        if(BaseUtils.isEmpty(ids)){
            return new Response(3052); //必填信息不能为空
        }
        try {
            String bannerShowIdList = appIndexTagService.updateAll(ids);
            return new Response(1001, bannerShowIdList);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }
}
