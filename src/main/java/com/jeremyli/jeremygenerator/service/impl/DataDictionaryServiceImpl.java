package com.jeremyli.jeremygenerator.service.impl;


import com.jeremyli.jeremygenerator.entity.DataFieldEntity;
import com.jeremyli.jeremygenerator.mapper.DataDictMapper;
import com.jeremyli.jeremygenerator.service.DataDictionaryService;
import com.jeremyli.jeremygenerator.vo.ColumnVo;
import com.jeremyli.jeremygenerator.vo.TableVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataDictionaryServiceImpl implements DataDictionaryService {

    public static final Logger logger = LoggerFactory.getLogger(DataDictionaryServiceImpl.class);
    @Autowired
    DataDictMapper dataDictMapper;
    @Override
    public void getColumnsReg(TableVo tableVo) {
        logger.info("获取列信息...");
        List<ColumnVo> columnEntities = tableVo.getColumnEntities();
        ArrayList<CompletableFuture<ColumnVo>> futures = new ArrayList<>();
        for (ColumnVo columnVo : columnEntities) {
            CompletableFuture<ColumnVo> supplied = CompletableFuture.supplyAsync(() -> {
                List<DataFieldEntity> fieldEntities = dataDictMapper.getColumnInfoAfterReg("%" + columnVo.getColumnComment() + "%");
                if (fieldEntities == null || fieldEntities.size() == 0){
                    return columnVo;
                }
                DataFieldEntity afterReg = fieldEntities.get(0);
                logger.info("获取落标后信息: {}", afterReg);
                columnVo.setColumnIdAfterReg(afterReg.getFiledId())
                        .setColumnDescAfterReg(afterReg.getFiledComment())
                        .setColumnTypeAfterReg(afterReg.getFiledType())
                        .setColumnNameAfterReg(afterReg.getFiledNameEn())
                        .setColumnCommentAfterReg(afterReg.getFiledName());
                return columnVo;
            });
            futures.add(supplied);
        }
        CompletableFuture<List<ColumnVo>> completableFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<ColumnVo> columnVos = new ArrayList<>();
                    for (CompletableFuture<ColumnVo> future : futures) {
                        try {
                            columnVos.add(future.get());
                        } catch (Exception e) {
                            logger.error("获取列信息报错，原因: {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }
                    return columnVos;
                });
        try{
            tableVo.setColumnEntities(completableFuture.get().stream().sorted(Comparator.comparingLong(ColumnVo::getId)).collect(Collectors.toList()));
        }catch(Exception e){
            logger.error("获取列信息报错，原因: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<DataFieldEntity> queryData(String queryStr) {
        return dataDictMapper.getColumnInfoAfterReg("%" + queryStr + "%");
    }
}
