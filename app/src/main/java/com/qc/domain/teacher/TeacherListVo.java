package com.qc.domain.teacher;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigInteger;


@Data
@Accessors(chain = true)
public class TeacherListVo {

    private BigInteger id;
//    private String avatar;
    private String nickname;
//    private String realName;
    private Integer gender;
//    private String userIntro;

//@Override
//public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//    TeacherListVo teacherListVo = (TeacherListVo) o;
//    return nickname == teacherListVo.nickname && Objects.equals(gender, teacherListVo.gender);
//}
//    @Override
//    public int hashCode() {
//        return Objects.hash(nickname, gender);
//    }

}
