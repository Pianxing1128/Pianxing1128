package com.qc.domain;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentWpVo{
    private String courseName;
    private String nickName;
    private Integer pageNum;
    private String realName;
}
