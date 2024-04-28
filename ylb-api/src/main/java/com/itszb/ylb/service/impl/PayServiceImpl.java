package com.itszb.ylb.service.impl;

import com.itszb.ylb.mapper.AccountMapper;
import com.itszb.ylb.mapper.RechargeMapper;
import com.itszb.ylb.service.PayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class PayServiceImpl implements PayService {

    @Resource
    private RechargeMapper rechargeMapper;

    @Resource
    private AccountMapper accountMapper;

    @Transactional
    public void payResult(String rechargeNo, Integer status, String channel, Long uid, Double money) {
        rechargeMapper.update(rechargeNo, status, channel);

        if (status == 1) {
            accountMapper.increaseMoney(uid, money);
        }
    }
}
