package com.qc.module.appIndexBanner.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 轮播图
 * </p>
 *
 * @author qc1128
 * @since 2023-05-18
 */
@Data
@Accessors(chain = true)
public class AppIndexBanner{


    private BigInteger id;
    /**
     * 轮播图名字
     */
    private String bannerName;

    /**
     * 轮播图图片链接
     */
    private String bannerImage;

    /**
     * 轮播图文章链接
     */
    private String bannerLink;

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
