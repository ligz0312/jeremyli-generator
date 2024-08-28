package com.jeremyli.jeremygenerator.mapper;

import com.jeremyli.jeremygenerator.entity.JobInfo;
import com.jeremyli.jeremygenerator.vo.BatchJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchParamMapper {

    List<JobInfo> getAllJobs(@Param("batchJob") BatchJob batchJob);
}
