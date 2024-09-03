package com.jeremyli.jeremygenerator.mapper;


import com.jeremyli.jeremygenerator.entity.DataFieldEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataDictMapper  {

    public List<DataFieldEntity> getColumnInfoAfterReg(@Param("col") String col);

}
