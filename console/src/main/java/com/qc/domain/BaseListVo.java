package com.qc.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BaseListVo {

    private Integer pageSize;
    private Integer courseTotal;
    private Integer teacherTotal;
    private Integer userTotal;
    private Integer courseTagTotal;
    private Integer appIndexBannerTotal;
    private Integer courseTagRelationTotal;
    private List courseList;
    private List teacherList;
    private List userList;
    private List courseTagList;
    private List appIndexBannerList;
    private List courseTagRelationList;

}
