CREATE TABLE teacher_info (
    id bigint PRIMARY KEY AUTO_INCREMENT  NOT NULL  COMMENT '自增主键',
    teacher_name varchar(100)  NOT NULL  COMMENT '教师姓名',
    teacher_age int  NOT NULL  COMMENT '教师年龄',
    teacher_sex char(1)  NOT NULL  COMMENT '教师性别，0-女，1-男'
) ENGINE=INNODB COMMENT='教师表';