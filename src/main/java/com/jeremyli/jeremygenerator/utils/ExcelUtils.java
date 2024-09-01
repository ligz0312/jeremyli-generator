package com.jeremyli.jeremygenerator.utils;

import cn.hutool.core.util.StrUtil;
import com.jeremyli.jeremygenerator.vo.ColumnVo;
import com.jeremyli.jeremygenerator.vo.TableVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static TableVo getTableEntityInfoByGenerate(Sheet sheet) {
        Row row = sheet.getRow(1);
        TableVo tableEntity = new TableVo();
        tableEntity.setSchemeName(getCellValue(row.getCell(0)))
                .setTableName(getCellValue(row.getCell(1)))
                .setTableComment(getCellValue(row.getCell(2)))
                .setPartitioned(StrUtil.equalsIgnoreCase(getCellValue(row.getCell(3)), "Y"))
                .setPartitionType(getCellValue(row.getCell(4)))
                .setPartitionNameSuffix(getCellValue(row.getCell(5)));
        Map<String, String> partitionMap = new HashMap<>();
        int colLine = 3;
        while (true) {
            Row partitionRow = sheet.getRow(colLine);
            if (partitionRow.getCell(0) == null) {
                break;
            }
            partitionMap.put(getCellValue(partitionRow.getCell(0)), getCellValue(partitionRow.getCell(1)));
            colLine++;
        }
        tableEntity.setPartitionMap(partitionMap);
        return tableEntity;
    }

    public static TableVo getTableEntityInfoByDict(Sheet sheet) {
        Row row = sheet.getRow(1);
        TableVo tableEntity = new TableVo();
        tableEntity.setTableName(getCellValue(row.getCell(0)))
                .setTableComment(getCellValue(row.getCell(1)));
        List<ColumnVo> columnEntities = new ArrayList<>();
        int colLine = 1;
        Long colIdx = 1L;
        while (true) {
            Row colRow = sheet.getRow(colLine);
            if (colRow == null || getCellValue(colRow.getCell(2)) == null) {
                break;
            }
            ColumnVo column = new ColumnVo();
            column.setId(colIdx)
                    .setColumnName(getCellValue(colRow.getCell(2)))
                    .setColumnType(getCellValue(colRow.getCell(3)))
                    .setNotNull(StrUtil.equalsIgnoreCase(getCellValue(colRow.getCell(4)), "Y"))
                    .setPrimaryKey(StrUtil.equalsIgnoreCase(getCellValue(colRow.getCell(5)), "Y"))
                    .setDefaultValue(getCellValue(colRow.getCell(6)))
                    .setColumnComment(getCellValue(colRow.getCell(7)));
            columnEntities.add(column);
            colLine++;
            colIdx++;
        }
        tableEntity.setColumnEntities(columnEntities);
        return tableEntity;
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }
}
