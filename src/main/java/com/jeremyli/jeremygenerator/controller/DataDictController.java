package com.jeremyli.jeremygenerator.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeremyli.jeremygenerator.entity.DataFieldEntity;
import com.jeremyli.jeremygenerator.service.impl.DataDictionaryServiceImpl;
import com.jeremyli.jeremygenerator.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DataDictController {

    @Autowired
    DataDictionaryServiceImpl dataDictionaryService;

    @RequestMapping("/test")
    public Result<Object> test(){


        return Result.ok("success");

    }

    @RequestMapping("/query")
    public Result<Object> query(@RequestParam(defaultValue = "1")Integer page,
                                @RequestParam(defaultValue = "10")Integer size,
                                @RequestParam("queryStr") String queryStr){
        PageHelper.startPage(page, size);
        List<DataFieldEntity> dataFieldEntities = dataDictionaryService.queryData(queryStr);
        return Result.ok(new PageInfo<>(dataFieldEntities));
    }
}
