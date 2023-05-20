package com.qc.domain.course;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentWpVo{
    private String courseName;
    private String nickName;
    private Integer showTagId;
    private Integer pageNum;
    private Integer isVip;
    private Integer orderedType;
}
