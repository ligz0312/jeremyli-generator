package com.jeremyli.jeremygenerator.vo;

import com.jeremyli.jeremygenerator.entity.ColumnEntity;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class TableVo {

    private String schemeName;

    private String tableName;

    private List<ColumnVo> columnEntities;

    private String tableComment;

    private boolean multiPrimaryKey;

    /**
     * 索引列表 map<索引名称, list<字段名>>
     */
    private HashMap<String, List<List<String>>> indexesMap;

    private String storeEngine;

    private String tableCharset;

    private String tableDataSource;

    private String tableCollate;

    private String databaseType;

    private boolean writeDatabase;

    private String saveFilePath;

    private boolean isGenerateFile;

    private String crtName;

    private String updName;

    private String rmk;
}
