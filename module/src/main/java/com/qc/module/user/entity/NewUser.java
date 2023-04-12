package com.qc.module.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class NewUser {
    private BigInteger id;
    private String nickName;
}
