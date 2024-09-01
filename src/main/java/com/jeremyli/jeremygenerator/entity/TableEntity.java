package com.jeremyli.jeremygenerator.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private boolean isPartitioned;

    private String partitionType;

    private Map<String, String> partitionMap;

    private String partitionNameSuffix;

    private String partitionNamePrefix;

    private String primaryKey;

}
