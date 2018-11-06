package com.baobao;

import org.slf4j.Logger;

import java.sql.*;

/**
 * 需要先把当前系统用户jessin加入到hadoop的/usr/local/hadoop-2.7.3/etc/hadoop/core-site.xml中。
 * 在/home/jessin/Downloads/apache-hive-2.3.3-bin/conf/hive-site.xml中配置用户zhangsan
 * <p>
 * 1. 启动hadoop，查看9000端口是否启动：start-dfs.sh
 * 2. 启动hiveserver2，命令：hiveserver2
 * 3. 查看hive.log：tail -f /tmp/jessin/hive.log
 * 4. 使用hive命令查看hive数据：hive
 * 或者beeline -u jdbc:hive2://localhost:10000 -n zhangsan -p 123456789
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

        Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000/myhive", "zhangsan", "123456789");

        Statement stmt = con.createStatement();
        String tableName = "student";
        // TODO 如何设置权限
//        stmt.execute("drop table if exists " + tableName);
//        stmt.execute("create table " + tableName +
//                " (key int, value string)");
//        System.out.println("Create table success!");
//        // show tables
        String sql = "show tables '" + tableName + "'";
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            LOG.info("表存在：{}", res.getString(1));
        }

        // describe table
        sql = "describe " + tableName;
        res = stmt.executeQuery(sql);
        while (res.next()) {
            LOG.info("{}\t{}", res.getString(1), res.getString(2));
        }

        sql = "select * from " + tableName;
        LOG.info("Running: {}", sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            LOG.info("{}\t{}", String.valueOf(res.getInt(1)), res.getString(2));
        }

        // TODO select count(*)会导致OOM..
//        sql = "select count(1) from " + tableName;
//        LOG.info("Running: {}", sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            LOG.info("执行结果：{}", res.getString(1));
//        }
    }
}
