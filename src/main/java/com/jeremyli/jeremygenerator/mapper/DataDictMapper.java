package com.jeremyli.jeremygenerator.mapper;

import com.jeremyli.jeremygenerator.entity.DataFieldEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataDictMapper {

    public DataFieldEntity getColumnInfoAfterReg(@Param("col") String col);

}
