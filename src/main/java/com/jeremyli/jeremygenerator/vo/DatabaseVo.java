package com.jeremyli.jeremygenerator.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DatabaseVo {
    private String dbName;
    private String dbSchema;
}
