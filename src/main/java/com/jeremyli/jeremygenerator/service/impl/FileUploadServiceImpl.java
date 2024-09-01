package com.jeremyli.jeremygenerator.service.impl;

import com.jeremyli.jeremygenerator.controller.CodeGenerateController;
import com.jeremyli.jeremygenerator.entity.TableEntity;
import com.jeremyli.jeremygenerator.service.DataDictionaryService;
import com.jeremyli.jeremygenerator.service.FileUploadService;
import com.jeremyli.jeremygenerator.utils.ExcelUtils;
import com.jeremyli.jeremygenerator.vo.ColumnVo;
import com.jeremyli.jeremygenerator.vo.TableVo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Autowired
    DataDictionaryServiceImpl dataDictionaryService;
    @Override
    public List<TableVo> processFile(MultipartFile file) {
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
                TableVo tableEntity = ExcelUtils.getTableEntityInfoByDict(sheet);
                logger.info("表信息: {}", tableEntity);
                dataDictionaryService.getColumnsReg(tableEntity);
                tableEntities.add(tableEntity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tableEntities;
    }
}
