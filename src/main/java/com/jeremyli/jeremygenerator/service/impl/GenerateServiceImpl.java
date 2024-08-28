package com.jeremyli.jeremygenerator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.jeremyli.jeremygenerator.common.Constant;
import com.jeremyli.jeremygenerator.entity.BatchParam;
import com.jeremyli.jeremygenerator.entity.ColumnEntity;
import com.jeremyli.jeremygenerator.entity.JobInfo;
import com.jeremyli.jeremygenerator.entity.TableEntity;
import com.jeremyli.jeremygenerator.mapper.BatchParamMapper;
import com.jeremyli.jeremygenerator.mapper.TableMapper;
import com.jeremyli.jeremygenerator.service.GenerateService;
import com.jeremyli.jeremygenerator.vo.BatchJob;
import com.jeremyli.jeremygenerator.vo.TableVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class GenerateServiceImpl implements GenerateService {

    @Autowired
    TableMapper tableMapper;
    @Autowired
    BatchParamMapper batchParamMapper;

    public static final Logger logger = LoggerFactory.getLogger(GenerateServiceImpl.class);


    @Override
    public boolean saveConfigAndGenerate(List<TableVo> tableVos) throws Throwable {
        List<Boolean> list = new ArrayList<>();
        for (TableVo tableVo : tableVos) {
            list.add(saveTableVoSingleAndGenerateFile(tableVo));
        }
        return !list.contains(false);
    }

    /**
     * 1、只保存
     * 2、保存+生成文件
     * 3、保存+生成文件+执行
     * @param tableVo
     * @return
     */
    private boolean saveTableVoSingleAndGenerateFile(TableVo tableVo) throws Throwable{

        TableEntity tableEntity = vo2Entity(tableVo);
        tableMapper.saveTableInfos(tableEntity);
        if (tableVo.isGenerateFile()){
            FileOutputStream outputStream = new FileOutputStream(tableVo.getSaveFilePath() + System.currentTimeMillis() +".zip");
            ZipOutputStream zip = new ZipOutputStream(outputStream);
            generateTableFile(tableVo, zip);
        }

        return true;
    }

    private void generateTableFile(TableVo tableVo, ZipOutputStream zip) {

        Properties pro = new Properties();
        pro.setProperty("file.resource.loader.path", "src/main/resources");
        pro.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        Velocity.init(pro);
        HashMap<String, Object> map = new HashMap<>();

        map.put("tableName", tableVo.getTableName());
        map.put("columns", tableVo.getColumnEntities());
        map.put("tableComment", tableVo.getTableComment());
        map.put("storeEngine", tableVo.getStoreEngine());
        VelocityContext context = new VelocityContext(map);
        Template template = Velocity.getTemplate("template/ddl_create_table_mysql.vm", "UTF-8");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        if (!tableVo.isWriteDatabase()){
            tableMapper.createTable(writer.toString());;
        }
        try {
            //添加到zip
            zip.putNextEntry(new ZipEntry(tableVo.getTableName().toLowerCase() + ".sql"));
            IOUtils.write(writer.toString(), zip, "UTF-8");
            IOUtils.closeQuietly(writer);
            zip.closeEntry();
        } catch (IOException e) {
            logger.error("压缩失败...原因：{}", e.getMessage());
            throw new RuntimeException("压缩失败...原因："+  e.getMessage());
        }
    }

    /**
     * 对tableVo中的字段映射到entity中
     * @param tableVo
     * @return
     */
    private TableEntity vo2Entity(TableVo tableVo){
        TableEntity tableEntity = new TableEntity();

        // 处理列字段
        List<ColumnEntity> columnEntities = tableVo.getColumnEntities().stream().map(col -> {
            ColumnEntity column = new ColumnEntity();
            column.setAutoIncr(col.isAutoIncr() ? "1" : "0");
            column.setNotNull(col.isNotNull() ? "1" : "0");
            column.setPrimaryKey((!tableVo.isMultiPrimaryKey() && col.isPrimaryKey())? "1" : "0");
            column.setColumnName(col.getColumnName());
            column.setDefaultValue(col.isHasDefault() ? col.getDefaultValue() : null);
            column.setColumnType(col.getColumnType() + (col.getColumnLength() > 0 ? "(" + col.getColumnLength() + ")" : ""));
            column.setColumnComment(StrUtil.isNotEmpty(col.getColumnComment()) ? "COMMENT " + col.getColumnComment() : "");
            return column;
        }).collect(Collectors.toList());

        tableEntity.setSchemeName(tableVo.getSchemeName())
                .setTableDataSource(tableVo.getTableDataSource())
                .setColumnEntities(columnEntities)
                .setDatabaseType(tableVo.getDatabaseType())
                .setTableName(tableVo.getTableName())
                .setTableCharset(tableVo.getTableCharset())
                .setTableCollate(tableVo.getTableCollate())
                .setIndexes(getIndexList(tableVo.getIndexesMap()))
                .setStoreEngine(getEngineForDatabase(tableVo.getStoreEngine(), tableVo.getDatabaseType()))
                .setTableComment(StrUtil.isNotEmpty(tableVo.getTableComment()) ? "COMMENT='" + tableVo.getTableComment() + "'" : "");
        return tableEntity;
    }

    private String getEngineForDatabase(String storeEngine, String databaseType) {
        if (StrUtil.isNotEmpty(storeEngine)){
            return storeEngine;
        }
        return Constant.DATABASE_TYPE.ENGINE2DB.get(databaseType);
    }

    private List<String> getIndexList(HashMap<String, List<List<String>>> indexesMap) {
        if (CollectionUtil.isEmpty(indexesMap)){
            return null;
        }
        List<String> indexList = new ArrayList<>();
        indexesMap.forEach((idxType, cols) -> cols.forEach(col -> indexList.add(idxType + "(" + String.join(",", col)  +  ")")));
        return indexList;
    }


    @Override
    public void dealExcelData(MultipartFile file) {
        try(InputStream is = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(is)){

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell next = cellIterator.next();
                    System.out.println(next.toString());
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void generateBatchSQL(BatchJob batchJob) {
        // dev_job_info、dev_batch_date -- common
        List<JobInfo> jobIds = batchParamMapper.getAllJobs(batchJob);
        if (CollectionUtil.isEmpty(jobIds)){
            logger.error("无可导出的作业...");
            return;
        }
        Map<String, List<String>> batchParams = new HashMap<>();
        jobIds.forEach(jobInfo -> {
            List<String> tables = batchParamMapper.getBatchParams(jobInfo.getJobId());
            batchParams.put(jobInfo.getJobId(), tables);
        });

        // 生成sql
        batchParams.forEach((jobId, tables) -> {


        });


    }


}
