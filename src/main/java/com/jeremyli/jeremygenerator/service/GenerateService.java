package com.jeremyli.jeremygenerator.service;

import cn.hutool.core.util.StrUtil;
import com.jeremyli.jeremygenerator.entity.TableEntity;
import com.jeremyli.jeremygenerator.vo.BatchJob;
import com.jeremyli.jeremygenerator.vo.ColumnVo;
import com.jeremyli.jeremygenerator.vo.TableVo;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.jeremyli.jeremygenerator.utils.ExcelUtils.getCellValue;

public interface GenerateService {

    static final Logger logger = LoggerFactory.getLogger(GenerateService.class);

    static final Map<String, String> dbtypeMap = new HashMap<>();

    @PostConstruct
    default void init() {
        Properties pro = new Properties();
        pro.setProperty("file.resource.loader.path", "src/main/resources");
        pro.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        Velocity.init(pro);
        dbtypeMap.put("gaussdb", "generate_template/ddl_create_table_gaussdb.vm");
        dbtypeMap.put("mysql", "generate_template/ddl_create_table_mysql.vm");
    }

    default void saveConfigAndGenerate(List<TableVo> tableVos, String dbType){
        try{
            ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(Paths.get(  System.currentTimeMillis() + ".zip")));
            // 映射  vo -> entity
            for (TableVo tableVo : tableVos) {
                    TableEntity tableEntity = vo2Entity(tableVo);
                    // 生成sql映射map
                    Map<String, Object> map = getPropertyMap(tableEntity);
                    // 生成sql
                    VelocityContext context = new VelocityContext(map);
                    Template template = Velocity.getTemplate(dbtypeMap.get(dbType), "UTF-8");
                    StringWriter writer = new StringWriter();
                    template.merge(context, writer);
                    //添加到zip
                    zip.putNextEntry(new ZipEntry(tableVo.getTableName().toLowerCase() + ".sql"));
                    IOUtils.write(writer.toString(), zip, "UTF-8");
                    IOUtils.closeQuietly(writer);

            }
            zip.closeEntry();
        }catch(Exception e){
            logger.error("生成sql时报错，原因: {}", e.getMessage());
        }
    }

    Map<String, Object> getPropertyMap(TableEntity tableEntity);



    TableEntity vo2Entity(TableVo tableVo);

    default TableVo getTableEntityInfo(Sheet sheet) {
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

    default List<ColumnVo> getColumnEntities(Sheet sheet) {
        List<ColumnVo> columnEntities = new ArrayList<>();
        int colLine = 3;
        while (true) {
            Row colRow = sheet.getRow(colLine);
            if (colRow == null || getCellValue(colRow.getCell(2)) == null) {
                break;
            }
            ColumnVo column = new ColumnVo();
            column.setColumnName(getCellValue(colRow.getCell(2)))
                    .setColumnType(getCellValue(colRow.getCell(3)))
                    .setNotNull(StrUtil.equalsIgnoreCase(getCellValue(colRow.getCell(4)), "Y"))
                    .setPrimaryKey(StrUtil.equalsIgnoreCase(getCellValue(colRow.getCell(5)), "Y"))
                    .setDefaultValue(getCellValue(colRow.getCell(6)))
                    .setColumnComment(getCellValue(colRow.getCell(7)));

            columnEntities.add(column);
            colLine++;
        }
        return columnEntities;
    }

    default void dealExcelData(MultipartFile file, String dbType){
        List<TableVo> tableEntities = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            // 遍历每个Sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if ("模板".equals(sheet.getSheetName())) {
                    logger.info("跳过名称为“模版”的Sheet");
                    continue;  // 跳过名称为“模版”的Sheet
                }
                logger.info("开始处理Sheet: {}", sheet.getSheetName());
                // 处理表信息
                TableVo tableEntity = getTableEntityInfo(sheet);
                logger.info("表信息: {}", tableEntity);
                // 处理列字段
                List<ColumnVo> columnEntities = getColumnEntities(sheet);
                logger.info("列字段: {}", columnEntities);
                // 处理主键
                List<String> pkList = new ArrayList<>();
                columnEntities.forEach(column -> {
                    if (column.isPrimaryKey()) {
                        pkList.add(column.getColumnName());
                    }
                });
                logger.info("主键: {}", pkList);
                tableEntity.setColumnEntities(columnEntities);
                tableEntity.setPrimaryKey(String.join(",", pkList));
                tableEntities.add(tableEntity);
            }
            saveConfigAndGenerate(tableEntities, dbType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void generateBatchSQL(BatchJob batchJob);


}
