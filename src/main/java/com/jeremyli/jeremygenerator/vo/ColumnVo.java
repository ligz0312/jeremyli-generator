package com.jeremyli.jeremygenerator.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ColumnVo {

    @NotNull(message = "列名不能为空")
    private String columnName;

    private String columnType;

    private int columnLength;

    private boolean primaryKey;

    private boolean autoIncr;

    private boolean notNull;

    private boolean hasDefault;

    private String defaultValue;

    private String columnComment;
}
