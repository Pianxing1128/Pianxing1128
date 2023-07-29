package com.qc.controller.course;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qc.annotations.VerifiedUser;
import com.qc.domain.BaseListVo;
import com.qc.domain.ImageVo;
import com.qc.domain.course.CommentWpVo;
import com.qc.domain.course.CourseInfoVo;
import com.qc.domain.course.CourseListVo;
import com.qc.module.course.entity.Course;
import com.qc.module.course.service.BaseCourseService;
import com.qc.module.course.service.CourseService;
import com.qc.module.course.service.CourseTagRelationService;
import com.qc.module.course.service.CourseTagService;
import com.qc.module.teacher.entity.Teacher;
import com.qc.module.teacher.service.TeacherService;
import com.qc.module.user.entity.User;
import com.qc.module.user.service.UserService;
import com.qc.utils.BaseUtils;
import com.qc.utils.ImageUtils;
import com.qc.utils.Response;
import com.qc.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseTagRelationService courseTagRelationService;
    @Autowired
    private CourseTagService courseTagService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseCourseService baseCourseService;

    @RequestMapping("/course/info")
    public Response showCourseDetailById(@VerifiedUser User loginUser,
                                         @RequestParam(name="id") BigInteger id) {

        if(BaseUtils.isEmpty(loginUser)){
            return new Response(1002);
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        Course course = courseService.getById(id);
        if(BaseUtils.isEmpty(course)){
           return new Response(3001);
        }
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(!BaseUtils.isEmpty(teacher)){
            courseInfoVo.setTeacherName(teacher.getRealName());
        }
        User user = userService.getById(teacher.getUserId());
        if(!BaseUtils.isEmpty(user)){
            courseInfoVo.setTeacherIntro(user.getUserIntro());
        }
        //根据id = courseId 得到 tagId, 根据tagId = tag表里的id 获得 tag
        String tagIds = courseTagRelationService.getTagIds(id);
        if(!BaseUtils.isEmpty(tagIds)){
            List<String> tags = courseTagService.getTagsByTagIds(tagIds);
            if(!BaseUtils.isEmpty(tags)){
                courseInfoVo.setTags(tags);
            }
        }
        courseInfoVo.setCourseName(course.getCourseName());
        courseInfoVo.setCourseSubName(course.getCourseSubName());
        courseInfoVo.setCourseCount(course.getCourseCount());
        courseInfoVo.setCourseCount(course.getCourseCount());
        courseInfoVo.setCourseTime(course.getCourseTime());
        courseInfoVo.setCourseIntro(course.getCourseIntro());
        courseInfoVo.setCoursePrice(BaseUtils.formatPrice(course.getCoursePrice()));

        List<String> courseList = Arrays.asList(course.getCourseImage().split("\\$"));
        Float ar;
        List<ImageVo> imageVoList = new ArrayList<>();
        for(String s:courseList){
            ImageVo imageVo = new ImageVo();
            imageVo.setImage(s);
            int[] wxh = ImageUtils.getImageWidthAndHeight(s);
            ar = ((float)wxh[0] / (float)wxh[1]);
            imageVo.setAr(ar);
            imageVoList.add(imageVo);
        }
        courseInfoVo.setImagesVo(imageVoList);

        return new Response(1001,courseInfoVo);
    }

    @RequestMapping("/course/list")
    public Response courseList(@RequestParam(required = false,name = "wp")String wp,
                               @RequestParam(required = false,name = "courseName") String courseName,
                               @RequestParam(required = false,name = "nickName") String nickName,
                               @RequestParam(required = false,name = "showTagId")Integer showTagId,
                               @RequestParam(required = false,name = "isVip")Integer isVip,
                               @RequestParam(required = false,name = "orderedType")Integer orderedType){

        CommentWpVo wpVo = new CommentWpVo();
        if (BaseUtils.isEmpty(wp)) {
            wpVo.setCourseName(courseName);
            wpVo.setNickName(nickName);
            wpVo.setShowTagId(showTagId);
            wpVo.setIsVip(isVip);
            wpVo.setOrderedType(orderedType);
            wpVo.setPageNum(1);
        } else {
            try {
                byte[] decodeWp = Base64.getUrlDecoder().decode(wp.getBytes(StandardCharsets.UTF_8));
                wpVo = JSON.parseObject(decodeWp, CommentWpVo.class);
            }catch (Exception e){
                return new Response(4004);
            }
        }

        Integer pageSize = Integer.valueOf(SpringUtils.getProperty("application.pagesize"));
        List<Course> courseList = baseCourseService.getCourseByCourseNameAndNickNameAndShowTagIdAndOrderedTypeAndIsVip(wpVo.getPageNum(), pageSize, wpVo.getCourseName(),wpVo.getNickName(),wpVo.getShowTagId(),wpVo.getOrderedType(),wpVo.getIsVip());

        if(courseList.size()==0){
            return new Response(3001);
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
            if(teacherRealNamesMap.containsKey(c.getTeacherId())){
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
            courseListVo.setCoursePrice(BaseUtils.formatPrice(c.getCoursePrice()));
            courseListVo.setPurchasedTotal(c.getPurchasedTotal());
            courseListVo.setIsVip(c.getIsVip());
            courseListVo.setIsMarketable(c.getIsMarketable());

            List<String> images = Arrays.asList(c.getCourseImage().split("\\$"));
            ImageVo imageVo = new ImageVo();
            imageVo.setImage(images.get(0));

            int[] wxh = ImageUtils.getImageWidthAndHeight(imageVo.getImage());
            Float ar = ((float)wxh[0] / (float)wxh[1]);
            imageVo.setAr(ar);
            courseListVo.setWallImage(imageVo);
            String tagIds = courseTagRelationService.getTagIds(c.getId());
            List<String> tags = courseTagService.getTagsByTagIds(tagIds);
            courseListVo.setTags(tags);
            list.add(courseListVo);
        }
        baseListVo.setCourseList(list);
        return new Response(1001,baseListVo);
    }

}