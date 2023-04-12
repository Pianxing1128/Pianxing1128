package com.qc.oss;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author qc112
 */
@Data
@Accessors(chain = true)
public class Page {

    private  Integer pageNum;
    private  Integer pageSize;


}
