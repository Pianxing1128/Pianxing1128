package com.qc.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qc.common.BaseResponse;
import com.qc.common.ResultUtils;
import com.qc.domain.*;
import com.qc.entity.*;
import com.qc.service.CourseService;
import com.qc.service.TeacherService;
import com.qc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CourseController {

    @Resource
    private CourseService courseService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private UserService userService;
    private String message;

    @RequestMapping("/course/info")
    public CourseInfoVo showCourseDetailById(BigInteger id) {

        CourseInfoVo entry = new CourseInfoVo();
        Course course = courseService.getById(id);
        Teacher teacher = teacherService.getById(course.getTeacherId());
        User user = userService.getById(teacher.getUserId());
        entry.setCourseName(course.getCourseName());
        entry.setCourseSubName(course.getCourseSubName());
        entry.setCourseCount(course.getCourseCount());
        entry.setCourseCount(course.getCourseCount());
        entry.setCourseTime(course.getCourseTime());
        entry.setCourseIntro(course.getCourseIntro());
        entry.setTeacherName(teacher.getRealName());
        entry.setTeacherIntro(user.getUserIntro());
        List<String> images = Arrays.asList((course.getCourseImage().split("\\$")));
        entry.setCourseImages(images);
        entry.setCoursePrice(course.getCoursePrice());
        return entry;
    }

    @RequestMapping("/course/list")
    public BaseResponse courseList(@RequestParam(required = false, name = "courseName") String courseName,
                                   @RequestParam(required = false, name = "nickName") String nickName,
                                   @RequestParam(required = false,name="realName")String realName,
                                   @RequestParam(required = false,name = "wp")String wp){

        CommentWpVo wpVo = new CommentWpVo();
        if (wp == null) {
            wpVo.setCourseName(courseName);
            wpVo.setNickName(nickName);
            wpVo.setRealName(realName);
            wpVo.setPageNum(1);
        } else {
            byte[] decodeWp = Base64.getUrlDecoder().decode(wp.getBytes(StandardCharsets.UTF_8));
            wpVo = JSON.parseObject(decodeWp, CommentWpVo.class);
        }
        Integer pageSize =12;
        List<Course> courseList = courseService.getCourseByCourseNameAndNickName(wpVo.getPageNum(), pageSize, wpVo.getCourseName(),wpVo.getNickName());

        if(courseList.size()==0){
            return ResultUtils.error(40040,"请求查询不到");
        }


        BaseListVo baseListVo = new BaseListVo();
        baseListVo.setIsEnd(courseList.size()<pageSize);

        wpVo.setPageNum(wpVo.getPageNum()+1);
        String wps = JSONObject.toJSONString(wpVo);
        byte[] encodeWp = Base64.getUrlEncoder().encode(wps.getBytes(StandardCharsets.UTF_8));
        baseListVo.setWp(new String(encodeWp, StandardCharsets.UTF_8).trim());

        List<BigInteger> teacherIds = courseList.stream().map(Course::getTeacherId).collect(Collectors.toList());
        List<Teacher> teacherList = teacherService.getByIds(teacherIds);
        Map<BigInteger, Teacher> teacherRealNamesMap = teacherList.stream().collect(Collectors.toMap(Teacher::getId, Function.identity()));
        List<BigInteger> userIds =  teacherList.stream().map(Teacher::getUserId).collect(Collectors.toList());
        List<User> userList = userService.getByIds(userIds);
        Map<BigInteger,User> userNickNamesMap = userList.stream().collect(Collectors.toMap(User::getId,Function.identity()));

        List<CourseListVo> list = new ArrayList<>();
        for (Course c : courseList) {

            CourseListVo courseListVo = new CourseListVo();
            courseListVo.setId(c.getId());
            courseListVo.setCourseName(c.getCourseName());
            if(teacherRealNamesMap.containsKey(c.getTeacherId())){  //通过course的teacherId拿到 teacher表里的realName
                Teacher teacher = teacherRealNamesMap.get(c.getTeacherId());
                courseListVo.setTeacherRealName(teacher.getRealName());
                if(userNickNamesMap.containsKey(teacher.getUserId())){
                   courseListVo.setTeacherNickName(userNickNamesMap.get(teacher.getUserId()).getNickName());
                }else {
                    continue;
                }
            }else {
                continue;
            }
            courseListVo.setCourseCount(c.getCourseCount());
            courseListVo.setCoursePrice(c.getCoursePrice());

            WallImageVo wallImageVo = new WallImageVo();
            String wallImage;
            Float ar=null;
            List<String> images = Arrays.asList((c.getCourseImage().split("\\$")));
            if(images.size()==0){ //如果得不到images
                continue;
            }
            wallImage = images.get(0);

            String[] wallImageSplit = wallImage.split("_");
            if(wallImageSplit.length==0){ //如果名字里没有"_"
                continue;
            }
            String wxhANDSuffix = wallImageSplit[wallImageSplit.length - 1];
            if(wxhANDSuffix==null){ //如果得不到名字和后缀
                continue;
            }
            String[] wxhANDSuffixSplit = wxhANDSuffix.split("\\.");
            if(wxhANDSuffixSplit.length==0){ //如果没有"."
                continue;
            }
            String wxh = wxhANDSuffixSplit[0];
            if(wxh==null){  //如果得不到 点前面的 wxh
                continue;
            }
            String[] wxhSplit = wxh.split("x");
            if(wxhSplit==null){ //如果没有"x"
                continue;
            }
            if(wxhSplit.length!=2){ //如果不是两个
                continue;
            }
            String width = wxhSplit[0];
            String height = wxhSplit[1];

            if(StringUtils.isNumeric(width)==false || StringUtils.isNumeric(height)==false){ //如果这俩不是数字
                continue;
            }
            Float w = Float.valueOf(width);
            Float h = Float.valueOf(height);
            ar = w/h;

            wallImageVo.setAr(ar);
            wallImageVo.setWallImage(wallImage);
            courseListVo.setWallImageVo(wallImageVo);

            list.add(courseListVo);
        }
        baseListVo.setCourseList(list);
        return ResultUtils.success(baseListVo);
    }

    @RequestMapping("/course/test")
    public BaseListVo courseTest(@RequestParam(required = false, name = "courseName") String courseName,
                                 @RequestParam(required = false, name = "nickName") String nickName,
                                 @RequestParam(required = false, name="realName")String realName,
                                 @RequestParam(required = false, name = "wp")String wp) {
        CommentWpVo wpVo = new CommentWpVo();
        if (wp == null) {
            wpVo.setCourseName(courseName);
            wpVo.setNickName(nickName);
            wpVo.setRealName(realName);
            wpVo.setPageNum(1);
        } else {
            byte[] decodeWp = Base64.getUrlDecoder().decode(wp.getBytes(StandardCharsets.UTF_8));
            wpVo = JSON.parseObject(decodeWp, CommentWpVo.class);
        }
        Integer pageSize =12;
        List<NewCourse> courseTeacherList = courseService.getCourseByRealName(wpVo.getPageNum(), pageSize, wpVo.getRealName(),wpVo.getNickName());
        log.info(String.valueOf(courseTeacherList));
        BaseListVo baseListVo = new BaseListVo();
        baseListVo.setIsEnd(courseTeacherList.size()<pageSize);

        wpVo.setPageNum(wpVo.getPageNum()+1);
        String wps = JSONObject.toJSONString(wpVo);
        byte[] encodeWp = Base64.getUrlEncoder().encode(wps.getBytes(StandardCharsets.UTF_8));
        baseListVo.setWp(new String(encodeWp, StandardCharsets.UTF_8).trim());

        List<CourseListVo> list = new ArrayList<>();
        for (NewCourse c : courseTeacherList) {

            CourseListVo courseListVo = new CourseListVo();
            courseListVo.setId(c.getId());
            courseListVo.setCourseName(c.getCourseName());
            courseListVo.setCourseCount(c.getCourseCount());
            courseListVo.setCoursePrice(c.getCoursePrice());
            courseListVo.setTeacherRealName(c.getNewTeacher().getRealName());
            courseListVo.setTeacherNickName(c.getNewTeacher().getNewUser().getNickName());
            list.add(courseListVo);
        }
        baseListVo.setCourseList(list);
        return baseListVo;
    }
}

//
//public class SSS {
//
//    Course c;
//    WallImageAR wallImageAR = new WallImageAR();
//    String wallImage;
//    Float ar = null;
//    List<String> images = Arrays.asList((c.getCourseImage().split("\\$")));
//    if(images!=0){
//        wallImage = images.get(0);
//        wallImageAR.setWallImage(wallImage);
//        String[] wallImageSplit = wallImage.split("_");
//        if (wallImageSplit.length != 0) {
//            String wxhANDSuffix = wallImageSplit[wallImageSplit.length - 1];
//            if (wxhANDSuffix != null) {
//                String[] wxhANDSuffixSplit = wxhANDSuffix.split("\\.");
//                if (wxhANDSuffixSplit.length != 0) {
//                    String wxh = wxhANDSuffixSplit[0];
//                    if (wxh != null) {
//                        String[] wxhSplit = wxh.split("x");
//                        if (wxhSplit != null) {
//                            if (wxhSplit.length == 2) {
//                                String width = wxhSplit[0];
//                                String height = wxhSplit[1];
//                                if (width != null && height != null && height.matches("[0-9]*") == true) {
//                                    Float w = Float.valueOf(Integer.parseInt(width));
//                                    Float h = Float.valueOf(Integer.parseInt(height));
//                                    ar = w / h;
//                                } else {
//                                    continue;
//                                }
//                            } else {
//                                continue;
//                            }
//                        } else {
//                            continue;
//                        }
//                    } else {
//                        continue;
//                    }
//                } else {
//                    continue;
//                }
//            } else {
//                continue;
//            }
//        } else {
//            continue;
//        }
//    }else{
//        continue;
//    }
//
//
//}
