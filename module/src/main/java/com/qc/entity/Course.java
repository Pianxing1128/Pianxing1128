package com.qc.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.List;


@Data
@Accessors(chain = true)
public class Course {

        /**
         * CREATE TABLE `course` (
         * 	`id` bigint unsigned NOT NULL AUTO_INCREMENT,
         *  `teacher_id` bigint unsigned NOT NULL,
         * 	`course_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名称',
         * 	`course_sub_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程副标题',
         * 	`course_count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程数量',
         * 	`course_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程总时间',
         *  `course_image` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程图片',
         *   `course_intro` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程介绍',
         *   `course_price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程价格',
         *   `weight` bigint unsigned NOT NULL DEFAULT '1' COMMENT '权重',
         *   `update_time` int unsigned NOT NULL,
         *   `create_time` int unsigned NOT NULL,
         * 	 `is_deleted` tinyint unsigned DEFAULT '0' NOT NULL,
         *
         *    PRIMARY KEY (`id`)
         * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程详情'
         */

        private BigInteger id;
        private BigInteger teacherId;
        private String courseName;
        private String courseSubName;
        private String courseCount;
        private String courseTime;
        private String courseImage;
        private String courseIntro;
        private String coursePrice;
        private Integer weight;
        private Integer updateTime = (int)(System.currentTimeMillis()/1000);
        private Integer createTime;
        private Integer isDeleted;
        private Teacher teacher;

}