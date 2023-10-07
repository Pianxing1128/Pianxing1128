package com.qc.module.userShareType.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * <p>
 * 用户分享类型
 * </p>
 *
 * @author qc1128
 * @since 2023-08-15
 */
@Data
@Accessors(chain = true)
public class UserShareType {

    private BigInteger id;

    /**
     * 分享名称
     */
    private String shareName;

    /**
     * 更新时间
     */
    private Integer updateTime = BaseUtils.currentSeconds();

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 是否删除
     */
    private Integer isDeleted = 0;


}
