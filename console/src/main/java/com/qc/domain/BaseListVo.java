package com.qc.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BaseListVo {

    private Integer pageSize;

    private Integer teacherTotal;
    private List teacherList;

    private Integer userTotal;
    private List userList;

    private Integer courseTotal;
    private List courseList;

    private Integer courseTagTotal;
    private List courseTagList;

    private Integer courseTagRelationTotal;
    private List courseTagRelationList;

    private Integer appIndexBannerTotal;
    private List appIndexBannerList;

    private Integer appIndexTagTotal;
    private List appIndexTagList;

    private Integer appIndexTagIdRelationTotal;
    private List appIndexTagIdRelationList;


}
