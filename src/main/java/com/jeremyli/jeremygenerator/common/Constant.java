package com.jeremyli.jeremygenerator.common;

import java.util.HashMap;
import java.util.Map;

public class Constant {


    public static final Integer SC_OK_200 = 200;
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;

    /**
     * 数据库类型
     */
    public static class DATABASE_TYPE{
        public static final String MYSQL = "01";

        public static final String TDSQL = "02";

        public static final String TIDB = "03";

        public static final String ORACLE = "04";

        public static final String POSTGRESQL = "05";

        public static final String GAUSSDB = "06";

        public static final Map<String, String> ENGINE2DB = new HashMap<>();

        static {
            ENGINE2DB.put(MYSQL, "INNODB");
            ENGINE2DB.put(TDSQL, "INNODB");
            ENGINE2DB.put(TIDB, "INNODB");
            ENGINE2DB.put(ORACLE, "");
            ENGINE2DB.put(POSTGRESQL, "");
            ENGINE2DB.put(GAUSSDB, "ASTORE");
        }

    }


    public static class BATCH_JOB_TYPE{

        public static final Map<String, String> JOB_TYPES = new HashMap<>();

        static {
            JOB_TYPES.put("BATCH_LOAD", "01");
            JOB_TYPES.put("BATCH_ISSUE", "02");
            JOB_TYPES.put("BATCH_TRANSFER", "03");
            JOB_TYPES.put("BATCH_PROCESS", "04");
        }

    }
}
