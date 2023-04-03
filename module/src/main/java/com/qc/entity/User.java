package com.qc.entity;

import java.math.BigInteger;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * <p>
 * 用户详情
 * </p>
 *
 * @author qc1128
 * @since 2023-03-17
 */

@Data
@Accessors(chain = true)
public class User{

    private BigInteger id;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户简介
     */
    private String userIntro;

    /**
     * 出生日期
     */
    private Integer birthday;

    /**
     * 上次登陆ip地址
     */
    private String lastLoginIp;

    /**
     * 注册ip地址
     */
    private String registerIp;

    /**
     * 上次登陆时间
     */
    private Integer lastLoginTime;

    /**
     * 更新时间
     */
    private Integer updateTime = (int)System.currentTimeMillis()/1000;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * CREATE TABLE `user` (
     * 	 `id` bigint unsigned NOT NULL AUTO_INCREMENT,
     *   `avatar` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '用户头像',
     *   `nick_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
     *   `gender` tinyint unsigned NOT NULL COMMENT '用户性别',
     *   `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户邮箱',
     *   `user_intro` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '用户简介',
     *    `birthday` int unsigned NOT NUll COMMENT '出生日期',
     *    `last_login_time` int unsigned NOT NULL COMMENT '上次登陆时间',
     *    `last_login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上次登陆ip地址',
     *    `register_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '注册ip地址',
     *    `create_time` int unsigned NOT NULL,
     *    `update_time` int unsigned NOT NULL,
     *    `is_deleted` tinyint unsigned DEFAULT '0' NOT NULL,
     *
     *  PRIMARY KEY (`id`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户详情'
     */
}
