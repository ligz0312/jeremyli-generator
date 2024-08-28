package com.jeremyli.jeremygenerator.controller;

import cn.hutool.core.lang.Assert;
import com.jeremyli.jeremygenerator.entity.TableEntity;
import com.jeremyli.jeremygenerator.service.GenerateService;
import com.jeremyli.jeremygenerator.service.impl.GenerateServiceImpl;
import com.jeremyli.jeremygenerator.vo.BatchJob;
import com.jeremyli.jeremygenerator.vo.Result;
import com.jeremyli.jeremygenerator.vo.TableVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api")
public class CodeGenerateController {

    private static final Logger logger = LoggerFactory.getLogger(CodeGenerateController.class);

    @Autowired
    GenerateServiceImpl generateService;

    @RequestMapping("/test")
    public Result<Object> saveConfigAndGenerate(@RequestBody @Valid TableVo tableVos){
        logger.info(tableVos.toString());
//        // 判断空或null
//        Assert.notEmpty(tableVos);
//        // 表的列字段不能为空
//        Assert.notEmpty(tableVos.stream().map(TableVo::getColumnEntities).collect(Collectors.toList()));
//        boolean isGenerate = false;
//        String failedMsg = "";
//        try{
//            isGenerate = generateService.saveConfigAndGenerate(tableVos);
//        }catch(Throwable e){
//            logger.error("save table config failed ... reason: {}", e.getMessage());
//            failedMsg = e.getMessage();
//        }
//        return isGenerate ? Result.ok("success") : Result.error("error", failedMsg);
        return Result.ok("success");
    }

    @RequestMapping("/code")
    public Result<Object> saveConfigAndGenerate(@RequestBody @Valid List<TableVo> tableVos){
        logger.info(tableVos.toString());
//        // 判断空或null
//        Assert.notEmpty(tableVos);
//        // 表的列字段不能为空
//        Assert.notEmpty(tableVos.stream().map(TableVo::getColumnEntities).collect(Collectors.toList()));
//        boolean isGenerate = false;
//        String failedMsg = "";
//        try{
//            isGenerate = generateService.saveConfigAndGenerate(tableVos);
//        }catch(Throwable e){
//            logger.error("save table config failed ... reason: {}", e.getMessage());
//            failedMsg = e.getMessage();
//        }
//        return isGenerate ? Result.ok("success") : Result.error("error", failedMsg);
        return Result.ok("success");
    }

    @RequestMapping("/upload")
    public String uploadConfigData(@RequestParam("file")MultipartFile file){
        generateService.dealExcelData(file);
        return "success";
    }

    public Result<Object> generateBatchSQL(@RequestBody BatchJob BatchJob){
        generateService.generateBatchSQL(BatchJob);
        return Result.ok("success");
    }

}
