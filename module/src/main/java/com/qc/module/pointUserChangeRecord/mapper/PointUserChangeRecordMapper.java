package com.qc.module.pointUserChangeRecord.mapper;

import com.qc.module.pointUserChangeRecord.entity.PointUserChangeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户积分变化 Mapper 接口
 * </p>
 *
 * @author qc1128
 * @since 2023-08-08
 */
@Mapper
public interface PointUserChangeRecordMapper{

    int insert(@Param("pointUserChangeRecord") PointUserChangeRecord pointUserChangeRecord);
}
