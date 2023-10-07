package com.qc.module.courseLessonDetail.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 课程节课详情表
 * </p>
 *
 * @author qc1128
 * @since 2023-08-13
 */
@Data
@Accessors(chain = true)
public class CourseLessonDetail {

    private BigInteger id;

    /**
     * 课程id
     */
    private BigInteger courseId;

    /**
     * 节课名称
     */
    private String lessonName;

    /**
     * 节课链接
     */
    private String lessonLink;

    /**
     * 节课时间
     */
    private Integer lessonTime;

    /**
     * 节课类型
     */
    private Integer lessonType;

    /**
     * 节课类型
     */
    private Integer isMarketable;

    /**
     * 更新时间
     */
    private Integer updateTime = BaseUtils.currentSeconds();

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 是否删除
     */
    private Integer isDeleted = 0;


}
