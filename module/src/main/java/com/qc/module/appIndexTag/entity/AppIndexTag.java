package com.qc.module.appIndexTag.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.qc.utils.BaseUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigInteger;

/**
 * <p>
 * 标签展示
 * </p>
 *
 * @author qc1128
 * @since 2023-05-19
 */
@Data
@Accessors(chain = true)
public class AppIndexTag{

    private BigInteger id;
    /**
     * 标签名字
     */
    @TableField("show_tag_Name")
    private String showTagName;

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
