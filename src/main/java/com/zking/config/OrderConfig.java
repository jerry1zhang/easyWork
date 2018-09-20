package com.zking.config;

/**
 * orderConfig
 *
 * @anthor zhanghy9
 * @date 2018-05-14 10:20
 */
public class OrderConfig {
    /**
     * 入库订单
     */
    public final static int ORDER_IN_REPOSITORY = 0;
    /**
     * 出库订单
     */
    public final static int ORDER_OUT_REPOSITORY = 1;
    /**
     * 返厂订单
     */
    public final static int ORDER_BACK_REPOSITORY = 2;
    /**
     * 调拨订单
     */
    public final static int ORDER_MOVE_REPOSITORY = 3;

    /**
     * 订单正常
     */
    public final static int ORDER_STATUS_DO = 0;
    /**
     * 订单挂起
     */
    public final static int ORDER_STATUS_PAUSE = 1;
    /**
     * 订单结束
     */
    public final static int ORDER_STATUS_END = 2;

    public final static int BATCH_STATUS_DO = 0;
    public final static int BATCH_STATUS_PAUSE = 1;
    public final static int BATCH_STATUS_END = 2;

}
