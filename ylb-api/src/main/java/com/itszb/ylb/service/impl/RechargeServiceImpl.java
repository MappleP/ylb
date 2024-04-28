package com.itszb.ylb.service.impl;

import com.itszb.ylb.beans.Recharge;
import com.itszb.ylb.mapper.RechargeMapper;
import com.itszb.ylb.service.RechargeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RechargeServiceImpl implements RechargeService {

    @Resource
    private RechargeMapper rechargeMapper;

    public int add(Recharge recharge) {
        return rechargeMapper.add(recharge);
    }
}
