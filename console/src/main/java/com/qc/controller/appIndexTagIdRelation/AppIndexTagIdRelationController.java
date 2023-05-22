package com.qc.controller.appIndexTagIdRelation;

import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.appIndexTagIdRelation.AppIndexTagIdRelationVo;
import com.qc.module.appIndexTagIdRelation.entity.AppIndexTagIdRelation;
import com.qc.module.appIndexTagIdRelation.service.AppIndexTagIdRelationService;
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
public class AppIndexTagIdRelationController {
    @Autowired
    private AppIndexTagIdRelationService appIndexTagIdRelationService;

    @RequestMapping("/index/tag/id/relation/list")
    public Response indexTagList(@VerifiedUser User loginUser,
                                 @RequestParam(required = false,name="pageNum")Integer inputPageNum,
                                 @RequestParam(required = false,name="showTagName")Integer showTagId,
                                 @RequestParam(required = false,name="tagId")Integer tagId){

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

        List<AppIndexTagIdRelation> appIndexTagIdRelationList = appIndexTagIdRelationService.extractIndexTagIdRelationListForConsole(pageNum,pageSize,showTagId,tagId);
        List<AppIndexTagIdRelationVo> list = new ArrayList<>();
        for(AppIndexTagIdRelation i:appIndexTagIdRelationList){
            AppIndexTagIdRelationVo appIndexTagIdRelationVo = new AppIndexTagIdRelationVo();
            appIndexTagIdRelationVo.setId(i.getId());
            appIndexTagIdRelationVo.setShowTagId(i.getShowTagId());
            appIndexTagIdRelationVo.setTagId(i.getTagId());
            appIndexTagIdRelationVo.setUpdateTime(BaseUtils.timeStamp2Date(i.getUpdateTime()));
            appIndexTagIdRelationVo.setCreateTime(BaseUtils.timeStamp2Date(i.getCreateTime()));
            appIndexTagIdRelationVo.setIsDeleted(i.getIsDeleted());
            list.add(appIndexTagIdRelationVo);
        }

        baseListVo.setPageSize(pageSize);
        baseListVo.setAppIndexBannerTotal(appIndexTagIdRelationService.extractTotal());
        baseListVo.setAppIndexBannerList(list);

        return new Response(1001,baseListVo);
    }

    @RequestMapping("/index/tag/id/relation/insert")
    public Response indexTagInsert(@VerifiedUser User loginUser,
                                   @RequestParam(required = false,name = "id") BigInteger id,
                                   @RequestParam(name = "showTagId")BigInteger showTagId,
                                   @RequestParam(name = "tagId")BigInteger tagId){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        if(BaseUtils.isEmpty(showTagId) || BaseUtils.isEmpty(tagId)){
            return new Response(3051); //必填信息不能为空
        }

        try {
            appIndexTagIdRelationService.edit(id,showTagId,tagId);
            return new Response(1001);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/index/tag/id/relation/update")
    public Response indexTagUpdate(@VerifiedUser User loginUser,
                                   @RequestParam(name = "id")BigInteger id,
                                   @RequestParam(required = false,name = "showTagId")BigInteger showTagId,
                                   @RequestParam(required = false,name = "tagId")BigInteger tagId){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        try {
            appIndexTagIdRelationService.edit(id,showTagId,tagId);
            return new Response(1001);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }

    @RequestMapping("/index/tag/id/relation/delete")
    public Response indexTagDelete(@VerifiedUser User loginUser,
                                   @RequestParam(name="id")BigInteger id){

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }

        try {
            appIndexTagIdRelationService.delete(id);
            return new Response(1001);
        }catch(RuntimeException e) {
            return new Response(4004); // 链接超时
        }
    }
}
