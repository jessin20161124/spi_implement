package org.apache.hadoop.hive.contrib.auth;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.slf4j.Logger;

import javax.security.sasl.AuthenticationException;

/**
 * 这个自定义校验类，需要配置到/home/jessin/Downloads/apache-hive-2.3.3-bin/conf/hive-site.xml中，而且必须把这类打成jar，
 * 放到/home/jessin/Downloads/apache-hive-2.3.3-bin/lib下
 * 命令如下：
 * mvn clean package
 * mv /home/jessin/Documents/Program/Java/spi/spi_implement/target/spi_implement-1.0-SNAPSHOT.jar /home/jessin/Downloads/apache-hive-2.3.3-bin/lib
 */
public class CustomPasswdAuthenticator implements org.apache.hive.service.auth.PasswdAuthenticationProvider {

    private Logger LOG = org.slf4j.LoggerFactory.getLogger(CustomPasswdAuthenticator.class);

    private static final String HIVE_JDBC_PASSWD_AUTH_PREFIX = "hive.jdbc_passwd.auth.%s";

    private Configuration conf = null;

    public void Authenticate(String userName, String passwd)
            throws AuthenticationException {
        LOG.info("用户：{} 尝试登录...", userName);
        String passwdConf = getConf().get(String.format(HIVE_JDBC_PASSWD_AUTH_PREFIX, userName));
        if (passwdConf == null) {
            String message = "用户访问控制没有找到：" + userName;
            LOG.info(message);
            throw new AuthenticationException(message);
        }
        if (!passwd.equals(passwdConf)) {
            String message = "用户名字和密码失配，用户为：" + userName;
            throw new AuthenticationException(message);
        }
    }

    public Configuration getConf() {
        if (conf == null) {
            this.conf = new Configuration(new HiveConf());
        }
        return conf;
    }

    public void setConf(Configuration conf) {
        this.conf = conf;
    }


}