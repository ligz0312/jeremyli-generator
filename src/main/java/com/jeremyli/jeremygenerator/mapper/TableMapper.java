package com.jeremyli.jeremygenerator.mapper;

import com.jeremyli.jeremygenerator.entity.TableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TableMapper {
    void createTable(@Param("createSql") String sql);

    void saveTableInfos(TableEntity tableEntity);
}
