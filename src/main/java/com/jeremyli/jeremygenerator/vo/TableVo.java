package com.jeremyli.jeremygenerator.vo;

import com.jeremyli.jeremygenerator.entity.ColumnEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class TableVo {


    private String schemeName;

    @NotNull(message = "表名不能为空")
    private String tableName;

    @Valid
    @NotNull(message = "表不能没有列")
    private List<ColumnVo> columnEntities;

    private String tableComment;

    private boolean multiPrimaryKey;

    private String primaryKey;


    /**
     * 索引列表 map<索引名称, list<字段名>>
     */
    private HashMap<String, List<List<String>>> indexesMap;

    private String storeEngine = "orientation=row, store_type=astore";

    private boolean isPartitioned;

    private String partitionType;

    private Map<String, String> partitionMap;

    private String partitionNameSuffix;

    private String partitionNamePrefix;

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
