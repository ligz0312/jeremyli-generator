package com.jeremyli.jeremygenerator.mapper;

import com.jeremyli.jeremygenerator.vo.DatabaseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DatabaseMapper {

    List<String> getDatabaseTables(@Param("dataBaseVo") DatabaseVo dataBaseVo);
}
