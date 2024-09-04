package com.jeremyli.jeremygenerator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.jeremyli.jeremygenerator.common.Constant;
import com.jeremyli.jeremygenerator.entity.ColumnEntity;
import com.jeremyli.jeremygenerator.entity.TableEntity;
import com.jeremyli.jeremygenerator.mapper.BatchParamMapper;
import com.jeremyli.jeremygenerator.mapper.ParamCodeMapper;
import com.jeremyli.jeremygenerator.mapper.TableMapper;
import com.jeremyli.jeremygenerator.service.GenerateService;
import com.jeremyli.jeremygenerator.vo.BatchJob;
import com.jeremyli.jeremygenerator.vo.ColumnVo;
import com.jeremyli.jeremygenerator.vo.DatabaseVo;
import com.jeremyli.jeremygenerator.vo.TableVo;
import lombok.extern.slf4j.Slf4j;
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
public class MySQLGenerateServiceImpl implements GenerateService {

    @Autowired
    TableMapper tableMapper;
    @Autowired
    BatchParamMapper batchParamMapper;
    @Autowired
    ParamCodeMapper paramCodeMapper;

    private Map<String, List<String>> jobBatchTables = new HashMap<>();

//    @PostConstruct
    public void initData() {
        try {
            Constant.BATCH_JOB_TYPE.JOB_TYPES.forEach((k, v) -> {
                jobBatchTables.put(k, paramCodeMapper.getParamValue(k));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static final Logger logger = LoggerFactory.getLogger(MySQLGenerateServiceImpl.class);


    @Override
    public Map<String, Object> getPropertyMap(TableEntity tableEntity) {
        return null;
    }

    /**
     * 对tableVo中的字段映射到entity中
     *
     * @param tableVo
     * @return
     */
    public TableEntity vo2Entity(TableVo tableVo) {
        return null;
    }

    @Override
    public void generateBatchSQL(BatchJob batchJob) {

    }

    @Override
    public List<String> getDatabaseTables(DatabaseVo dataBaseVo) {
        return null;
    }


    private List<String> getIndexList(HashMap<String, List<List<String>>> indexesMap) {
        if (CollectionUtil.isEmpty(indexesMap)) {
            return null;
        }
        List<String> indexList = new ArrayList<>();
        indexesMap.forEach((idxType, cols) -> cols.forEach(col -> indexList.add(idxType + "(" + String.join(",", col) + ")")));
        return indexList;
    }











}
