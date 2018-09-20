package com.zking.config;

public enum ConfigCode {
    bushu("部署提交信息为空"),
    USER_ERROR_1("用户密码错误"),
    USER_ERROR_2("用户不存在"),
    page_title_1("保健品"),
    page_title_2("销售信息管理系统"),
    LEVEL_BIGMENU("1"),
    LEVEL_SMALLMENU("2"),
    ADD_USER_ERROR("创建用户失败");

	private final String value;


    ConfigCode(String value)
    {

        this.value = value;
    }

    public String getValue()
    {

        return value;
    }

}
