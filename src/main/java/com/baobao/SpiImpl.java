package com.baobao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zexin.guo
 * @create 2018-03-29 下午5:25
 **/
public class SpiImpl implements ISpi {
    private Logger logger = LoggerFactory.getLogger(getClass());
    public void say() {
        System.out.println("hello world");
    }
}
