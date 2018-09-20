package com.zking.biz;

import org.apache.log4j.Logger;

public class BaseBizImpl {
    protected Logger logger = Logger.getLogger(this.getClass());

    public static void logBefore(Logger logger, String interfaceName){
        logger.info("");
        logger.info("start");
        logger.info(interfaceName);
    }

    public static void logAfter(Logger logger){
        logger.info("end");
        logger.info("");
    }
}
