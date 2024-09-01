package com.jeremyli.jeremygenerator.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 列实体类
 */
@Data
@Accessors(chain = true)
public class ColumnEntity {
    private String columnName;

    private String columnType;

    private boolean primaryKey;

    private String autoIncr;

    private boolean notNull;

    private String defaultValue;

    private String columnComment;

    private String crtName;

    private String updName;

    private String rmk;

}
