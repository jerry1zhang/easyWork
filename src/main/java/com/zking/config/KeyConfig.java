package com.zking.config;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class KeyConfig {
    private final static String KEY = "jerry1zhang@outlook.com";

    public final static int pagenum = 2;//一页多少条数据

    public final static int CAIGOUENDDAY = 30;//默认采购结束天数


    public static String getKEY() {
        return KEY;
    }
}
