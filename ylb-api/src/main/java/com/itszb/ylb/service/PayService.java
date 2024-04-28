package com.itszb.ylb.service;

public interface PayService {
    /**
     *
     * @param rechargeNo 订单号
     * @param status 支付状态
     * @param channel 充值渠道
     * @param uid   用户id
     * @param money 充值金额
     */
    void payResult(String rechargeNo, Integer status, String channel, Long uid, Double money);
}
