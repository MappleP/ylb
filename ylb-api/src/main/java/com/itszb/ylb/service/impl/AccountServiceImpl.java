package com.itszb.ylb.service.impl;

import com.itszb.ylb.mapper.AccountMapper;
import com.itszb.ylb.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.service.impl
 * @className: AccountServiceImpl
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/23 10:28
 * @version: 1.0
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public Double getBalance(Long uid) {
        return accountMapper.getBalance(uid);
    }
}
