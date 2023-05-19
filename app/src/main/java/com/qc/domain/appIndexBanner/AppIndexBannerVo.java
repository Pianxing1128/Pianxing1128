package com.qc.domain.appIndexBanner;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AppIndexBannerVo {

    private String bannerImage;
    private String bannerLink;

}
