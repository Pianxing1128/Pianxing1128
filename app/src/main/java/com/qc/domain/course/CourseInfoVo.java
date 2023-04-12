package com.qc.domain.course;

import com.qc.domain.ImageVo;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
public class CourseInfoVo {


    private String courseName;
    private String courseSubName;
    private String courseCount;
    private String courseTime;
    private String courseIntro;
    private String teacherName;
    private String teacherIntro;
    private List<ImageVo> imagesVo;
    private String coursePrice;

}
