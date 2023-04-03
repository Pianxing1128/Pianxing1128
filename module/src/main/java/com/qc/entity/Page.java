package com.qc.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author qc112
 */
@Data
@Accessors(chain = true)
public class Page {

    private  Integer pageNum;
    private  Integer pageSize;


}
