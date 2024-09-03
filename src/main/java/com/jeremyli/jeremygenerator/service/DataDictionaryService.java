package com.jeremyli.jeremygenerator.service;

import com.jeremyli.jeremygenerator.entity.DataFieldEntity;
import com.jeremyli.jeremygenerator.vo.ColumnVo;
import com.jeremyli.jeremygenerator.vo.TableVo;

import java.util.List;

public interface DataDictionaryService {

    void getColumnsReg(TableVo tableVo);

//    List<DataFieldEntity> queryData(String queryStr);

    List<DataFieldEntity> queryData(String queryStr);
}
