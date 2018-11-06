package com.baobao;

import org.slf4j.Logger;

import java.sql.*;

/**
 * hdfs的数据节点不要重新格式化
 * 需要先把当前系统用户jessin加入到hadoop的/usr/local/hadoop-2.7.3/etc/hadoop/core-site.xml中。
 * 在/home/jessin/Downloads/apache-hive-2.3.3-bin/conf/hive-site.xml中配置用户jessin
 * 注意jessin与启动hadoop的linux 账号一样，避开一些权限问题。
 * <p>
 * 1. 启动hadoop，查看9000端口是否启动：start-dfs.sh
 * 2. 启动hiveserver2，命令：hiveserver2
 * 3. 查看hive.log：tail -f /tmp/jessin/hive.log
 * 4. 使用hive命令查看hive数据：hive 或者beeline -u jdbc:hive2://localhost:10000 -n jessin -p 123456789
 * <p>
 * 查看hadoop相关节点：http://localhost:50070/dfshealth.html#tab-overview
 *
 * @author zexin.guo
 * @create 2018-11-05 下午8:58
 **/
public class HiveJdbcDemo {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(HiveJdbcDemo.class);

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args)
            throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000/myhive", "jessin", "123456789");

        Statement stmt = con.createStatement();
        String tableName = "hello";

//        init(stmt, tableName);

        String selectSql = "select * from " + tableName;
        LOG.info("Running: {}", selectSql);
        ResultSet selectSqlResult = stmt.executeQuery(selectSql);
        while (selectSqlResult.next()) {
            LOG.info("{}\t{}", selectSqlResult.getString(1), selectSqlResult.getInt(2));
        }

        // TODO select count(*)会导致OOM..
//        sql = "select count(1) from " + tableName;
//        LOG.info("Running: {}", sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            LOG.info("执行结果：{}", res.getString(1));
//        }
    }

    private static void init(Statement stmt, String tableName) throws SQLException {
        // TODO 如何设置权限
        stmt.execute("drop table if exists " + tableName);
        stmt.execute("create table " + tableName +
                "(key String, value int)" +
                "row format delimited fields terminated by ',' stored as textfile");

        String showTableSql = "show tables '" + tableName + "'";
        ResultSet showTableRes = stmt.executeQuery(showTableSql);
        if (showTableRes.next()) {
            LOG.info("表存在：{}", showTableRes.getString(1));
        }

        String loadDataSql = "load data local inpath '/home/jessin/kv.csv' into table  " + tableName;
        stmt.executeUpdate(loadDataSql);
    }
}
