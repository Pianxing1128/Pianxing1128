package com.qc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qc.domain.BaseListVo;
import com.qc.domain.CommentWpVo;
import com.qc.domain.TeacherListVo;
import com.qc.entity.Teacher;
import com.qc.service.TeacherService;
import com.qc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@Slf4j
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @Resource
    private UserService userService;

    @RequestMapping("/teacher/list")
    public void teacherList(@RequestParam(required = false, name = "nickName") String nickName,
                            @RequestParam(required = false, name = "wp") String wp) throws Exception {

        CommentWpVo wpVo = new CommentWpVo();
        if (wp == null) {
            wpVo.setPageNum(1);
            wpVo.setNickName(nickName);
        } else {
            byte[] decodeWp = Base64.getUrlDecoder().decode(wp.getBytes(StandardCharsets.UTF_8));
            wpVo = JSON.parseObject(decodeWp, CommentWpVo.class);
        }

        Integer pageSize = 12;
        List<Teacher> teachers = teacherService.getTeachersByNickName(wpVo.getPageNum(), pageSize, wpVo.getNickName());
        int size = teachers.size();

        BaseListVo baseListVo = new BaseListVo();
        baseListVo.setIsEnd(size < pageSize);

        wpVo.setPageNum(wpVo.getPageNum() + 1);
        String wps = JSONObject.toJSONString(wpVo);
        byte[] encodeWp = Base64.getUrlEncoder().encode(wps.getBytes(StandardCharsets.UTF_8));
        baseListVo.setWp(new String(encodeWp, StandardCharsets.UTF_8).trim());
        List<TeacherListVo> list = new ArrayList<>();
//        for(Teacher t: teachers){
//
//
//                }
//            list.setId(t.getId());
//            result.setGender(user.getGender());
//            result.setNickname(user.getNickName());
//            result.setRealName(t.getRealName());

//        }
//            list.add(teacherListVo);
//    }
//
//            baseListVo.setTeacherList(list);
//            return baseListVo;
//    }
    }
}
