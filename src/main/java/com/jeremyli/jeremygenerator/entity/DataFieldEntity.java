package com.jeremyli.jeremygenerator.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DataFieldEntity {

    private Long id;

    private String filedName;

    private String filedNameEn;

    private String filedType;

    private String filedLength;

    private String filedId;

    private String filedComment;

    private String filedDesc;
}
