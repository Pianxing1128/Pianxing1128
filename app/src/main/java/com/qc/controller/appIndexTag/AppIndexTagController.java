package com.qc.controller.appIndexTag;

import com.qc.domain.BaseListVo;
import com.qc.domain.appIndexTag.AppIndexTagVo;
import com.qc.module.appIndexTag.entity.AppIndexTag;
import com.qc.module.appIndexTag.service.AppIndexTagService;
import com.qc.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppIndexTagController {

    @Autowired
    private AppIndexTagService appIndexTagService;

    @RequestMapping("/app/index/tag/list")
    public Response appIndexTagList(){

        List<AppIndexTag> appIndexTagListForApp = appIndexTagService.getAppIndexTagListForApp();
        List<AppIndexTagVo> list = new ArrayList<>();
        for(AppIndexTag a:appIndexTagListForApp){
            AppIndexTagVo appIndexTagVo = new AppIndexTagVo();
            appIndexTagVo.setId(a.getId());
            appIndexTagVo.setShowTagName(a.getShowTagName());
            list.add(appIndexTagVo);
        }

        BaseListVo baseListVo = new BaseListVo();
        baseListVo.setIndexTagList(list);
        return new Response(1001,baseListVo);
    }
}
