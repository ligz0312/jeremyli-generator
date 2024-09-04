package com.jeremyli.jeremygenerator.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jeremyli.jeremygenerator.entity.ColumnEntity;
import com.jeremyli.jeremygenerator.entity.TableEntity;
import com.jeremyli.jeremygenerator.mapper.DatabaseMapper;
import com.jeremyli.jeremygenerator.service.GenerateService;
import com.jeremyli.jeremygenerator.vo.BatchJob;
import com.jeremyli.jeremygenerator.vo.ColumnVo;
import com.jeremyli.jeremygenerator.vo.DatabaseVo;
import com.jeremyli.jeremygenerator.vo.TableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GaussGenerateServiceImpl implements GenerateService {
    @Autowired
    DatabaseMapper databaseMapper;


    @Override
    public Map<String, Object> getPropertyMap(TableEntity tableEntity) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("columns", tableEntity.getColumnEntities());
        map.put("tableComment", tableEntity.getTableComment()); // 表注释
        map.put("primaryKey", tableEntity.getPrimaryKey());
        // orientation=row,store_type=astore
        map.put("storeEngine", tableEntity.getStoreEngine());
        if (!tableEntity.isPartitioned()){
            map.put("partitionInfo", null);
        }else {
            map.put("partitionInfo", tableEntity.getPartitionMap());
        }
        return map;

    }

    @Override
    public TableEntity vo2Entity(TableVo tableVo) {
        TableEntity tableEntity = new TableEntity();

        // 处理列字段
        List<ColumnEntity> columnEntities = tableVo.getColumnEntities().stream().map(col -> {
            ColumnEntity column = new ColumnEntity();
            column.setAutoIncr(col.isAutoIncr() ? "1" : "0");
            column.setNotNull(col.isNotNull());
            column.setPrimaryKey((col.isPrimaryKey()));
            column.setColumnName(col.getColumnName());
            column.setDefaultValue(col.getDefaultValue());
            column.setColumnType(col.getColumnType());
            column.setColumnComment(StrUtil.isNotEmpty(col.getColumnComment()) ? col.getColumnComment() : "");
            return column;
        }).collect(Collectors.toList());

        tableEntity.setSchemeName(tableVo.getSchemeName())
                .setTableDataSource(tableVo.getTableDataSource())
                .setColumnEntities(columnEntities)
                .setDatabaseType(tableVo.getDatabaseType())
                .setTableName(tableVo.getTableName())
                .setTableCharset(tableVo.getTableCharset())
                .setStoreEngine(tableVo.getStoreEngine())
                .setTableCollate(tableVo.getTableCollate())
                .setPrimaryKey(tableVo.getPrimaryKey())
                .setTableComment(StrUtil.isNotEmpty(tableVo.getTableComment()) ? tableVo.getTableComment() : "");
        return tableEntity;
    }


    @Override
    public void generateBatchSQL(BatchJob batchJob) {

    }

    @Override
    public List<String> getDatabaseTables(DatabaseVo dataBaseVo) {

        return databaseMapper.getDatabaseTables(dataBaseVo);
    }
}
