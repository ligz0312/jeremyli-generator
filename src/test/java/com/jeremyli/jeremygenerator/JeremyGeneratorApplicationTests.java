package com.jeremyli.jeremygenerator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JeremyGeneratorApplicationTests {

    @Test
    void contextLoads() {
        //
//    public static void main(String[] args) throws Exception {
//
//        TableEntity table = new TableEntity();
//        table.setTableComment("教师表");
//        table.setTableName("teacher_info");
//        table.setStoreEngine("INNODB");
//
//        ColumnEntity col0 = new ColumnEntity()
//                .setColumnName("id")
//                .setColumnComment("自增主键")
//                .setColumnType("bigint")
//                .setPrimaryKey(true)
//                .setAutoIncr(true)
//                .setNotNull(true);
//
//        ColumnEntity col1 = new ColumnEntity()
//                .setColumnName("teacher_name")
//                .setColumnComment("教师姓名")
//                .setColumnType("varchar(100)")
//                .setPrimaryKey(false)
//                .setNotNull(true);
//
//        ColumnEntity col2 = new ColumnEntity()
//                .setColumnName("teacher_age")
//                .setColumnComment("教师年龄")
//                .setColumnType("int")
//                .setPrimaryKey(false)
//                .setNotNull(true);
//
//        ColumnEntity col3 = new ColumnEntity()
//                .setColumnName("teacher_sex")
//                .setColumnComment("教师性别，0-女，1-男")
//                .setColumnType("char(1)")
//                .setPrimaryKey(false)
//                .setNotNull(true);
//
//        List<ColumnEntity> columns = new ArrayList<>(Arrays.asList(col0, col1, col2, col3));
//        table.setColumnEntities(columns);
//
//        Properties pro = new Properties();
//        pro.setProperty("file.resource.loader.path", "src/main/resources");
//        pro.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
//
//        Velocity.init(pro);
//
//        HashMap<String, Object> map = new HashMap<>();
//
//        System.out.println(table);
//
//        map.put("tableName", table.getTableName());
//        map.put("columns", table.getColumnEntities());
//        map.put("tableComment", table.getTableComment());
//        map.put("storeEngine", table.getStoreEngine());
//
//
//        VelocityContext context = new VelocityContext(map);
//
//        Template template = Velocity.getTemplate("template/ddl_create_table_mysql.vm", "UTF-8");
//
//        StringWriter writer = new StringWriter();
//
//        template.merge(context, writer);
//
//        boolean isCreateInDatabase = false;
//
//        if (isCreateInDatabase){
//            boolean isSuccess = createTableInDatabase(writer.toString());
//            System.out.println("创建结果："+ isSuccess);
//        }
//        FileOutputStream outputStream = new FileOutputStream("test.zip");
//        ZipOutputStream zip = new ZipOutputStream(outputStream);
//        try {
//            //添加到zip
//            zip.putNextEntry(new ZipEntry(table.getTableName().toLowerCase() + ".sql"));
//            IOUtils.write(writer.toString(), zip, "UTF-8");
//            IOUtils.closeQuietly(writer);
//
//            zip.closeEntry();
//        } catch (IOException e) {
//            System.out.println("压缩失败...");
//        }
//
//        System.out.println(writer.toString());
//    }
//    public static SqlSessionFactory sessionFactory;

    }

//    private static boolean createTableInDatabase(String string) throws Exception{
//        try(Reader reader = Resources.getResourceAsReader("mybatis-test-config.xml")){
//            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        }
//
//        try(SqlSession session = sessionFactory.openSession()){
//            TableMapper tableMapper = session.getMapper(TableMapper.class);
//            tableMapper.createTable(string);
//            return true;
//        }catch (Exception e){
//            System.out.println("执行失败，原因：" + e.getMessage());
//            return false;
//        }
//    }

}
