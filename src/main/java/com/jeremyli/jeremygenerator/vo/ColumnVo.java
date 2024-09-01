package com.jeremyli.jeremygenerator.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ColumnVo {

    private Long id;

    @NotNull(message = "列名不能为空")
    private String columnName;
    // 表字段类型 varchar(100)、int、decimal(21,3)、datetime、text
    private String columnType;

    private boolean primaryKey;

    private boolean autoIncr;

    private boolean notNull;

    private boolean hasDefault;

    private String defaultValue;

    private String columnComment;

    private String columnNameAfterReg = "";

    private String columnTypeAfterReg = "";

    private String columnCommentAfterReg = "";

    private String columnDescAfterReg = "";

    private String columnIdAfterReg = "";
}
