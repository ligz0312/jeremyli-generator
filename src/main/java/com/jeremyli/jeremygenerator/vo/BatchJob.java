package com.jeremyli.jeremygenerator.vo;

import lombok.Data;

@Data
public class BatchJob {
    /*
     * 1、加载作业
     * 2、加工作业
     * 3、文件生成作业
     * 4、文件下传作业
     */
    private String jobType;

    private String jobId;

    private String batchVersion;

    private String author;

}
