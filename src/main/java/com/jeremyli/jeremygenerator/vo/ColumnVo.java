package com.jeremyli.jeremygenerator.vo;

import lombok.Data;

@Data
public class ColumnVo {
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
