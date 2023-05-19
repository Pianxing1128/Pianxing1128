package com.qc.domain.appIndexBanner;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain=true)
public class AppIndexBannerVo {

    private BigInteger id;
    private String bannerName;
    private String bannerImage;
    private String bannerLink;
    private String updateTime;
    private String createTime;
    private Integer isDeleted;
}
