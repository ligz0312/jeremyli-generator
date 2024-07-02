package com.jeremyli.jeremygenerator.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;

/**
 * 表实体类
 */
@Data
@Accessors(chain = true)
public class TableEntity {

    private String schemeName;

    private String tableName;

    private List<ColumnEntity> columnEntities;

    private String tableComment;

    private List<String> indexes;

    private String storeEngine;

    private String tableCharset;

    private String tableDataSource;

    private String tableCollate;

    private String databaseType;


}
