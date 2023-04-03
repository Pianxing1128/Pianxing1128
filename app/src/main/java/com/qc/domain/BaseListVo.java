package com.qc.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
public class BaseListVo{

        private List courseList;
        private List teacherList;
        private String wp;
        private Boolean isEnd;

}
