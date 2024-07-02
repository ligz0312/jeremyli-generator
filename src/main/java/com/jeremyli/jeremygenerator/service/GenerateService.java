package com.jeremyli.jeremygenerator.service;

import com.jeremyli.jeremygenerator.entity.TableEntity;
import com.jeremyli.jeremygenerator.vo.TableVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GenerateService {


    boolean saveConfigAndGenerate(List<TableVo> tableVos) throws Throwable;

    void dealExcelData(MultipartFile file);
}
