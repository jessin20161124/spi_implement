package com.baobao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zexin.guo
 * @create 2018-04-18 下午4:16
 **/
public class LogbackTest {
    @Test
    public void testSlf4j() {
        Logger logger = LoggerFactory.getLogger(Object.class);
        logger.trace("=====trace=====");
        logger.debug("=====debug=====");
        logger.info("=====info=====");
        logger.warn("=====warn=====");
        logger.error("=====error=====");
    }
}
