package com.qc.domain.HttpResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResultVo {

    private Integer code;

    private String msg;

    private Object data;


}
