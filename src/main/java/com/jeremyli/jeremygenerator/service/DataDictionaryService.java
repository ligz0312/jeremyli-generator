package com.jeremyli.jeremygenerator.service;

import com.jeremyli.jeremygenerator.vo.ColumnVo;
import com.jeremyli.jeremygenerator.vo.TableVo;

import java.util.List;

public interface DataDictionaryService {

    void getColumnsReg(TableVo tableVo);
}
