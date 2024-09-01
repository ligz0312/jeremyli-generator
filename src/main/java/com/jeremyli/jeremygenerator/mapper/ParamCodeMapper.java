package com.jeremyli.jeremygenerator.mapper;

import com.jeremyli.jeremygenerator.entity.JobInfo;
import com.jeremyli.jeremygenerator.vo.BatchJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ParamCodeMapper {

    List<String> getParamValue(@Param("paramCode") String paramCode);
    void initData();
}
