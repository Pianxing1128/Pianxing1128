package com.qc.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain =true)
public class WallImageVo {
    private String wallImage;
    private Float ar;
}
