package com.qc.module.appIndexTagIdRelation.entity;

import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigInteger;

/**
 * <p>
 * 展示标签关系表
 * </p>
 *
 * @author qc1128
 * @since 2023-05-20
 */
@Data
@Accessors(chain = true)
public class AppIndexTagIdRelation{

    private BigInteger id;

    /**
     * 标签展示id
     */
    private BigInteger showTagId;

    /**
     * 标签id
     */
    private BigInteger tagId;

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
