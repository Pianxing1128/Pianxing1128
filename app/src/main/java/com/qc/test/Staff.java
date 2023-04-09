package com.qc.test;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Staff {
    private String name;
    private Integer age;
    private String telephone;

}
