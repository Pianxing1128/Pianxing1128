package com.qc.test;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain =true)
public class HotelVo {
    private String name;
    private String distance;
}
