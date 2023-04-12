package com.qc.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain =true)
public class ImageVo {

    private String Image;
    private Float ar;

}
