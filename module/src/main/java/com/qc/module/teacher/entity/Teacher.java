package com.qc.module.teacher.entity;

import java.math.BigInteger;
import java.util.Objects;

import com.qc.module.user.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Teacher{
    /**
     * 老师id
     */
    private BigInteger id;

    /**
     * 用户id
     */
    private BigInteger userId;

    /**
     * 教师真名
     */
    private String realName;

    /**
     * 入职时间
     */
    private String enrollmentTime;

    /**
     * 更新时间
     */
    private Integer updateTime = (int)System.currentTimeMillis()/1000;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    private User user;

    @Override
    public boolean equals(Object o){
        if(this == o){return true;}
        if(o==null||getClass()!=o.getClass()){return false;}
        Teacher that = (Teacher) o;
        return Objects.equals(id,that.id)&&
                Objects.equals(realName,that.realName);
    }
    @Override
    public int hashCode(){
        return Objects.hash(id,realName);
    }
}