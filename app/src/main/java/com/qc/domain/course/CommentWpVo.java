package com.qc.domain.course;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentWpVo{
    private String courseName;
    private String nickName;
    private String tag;
    private Integer pageNum;
    private String realName;
}
