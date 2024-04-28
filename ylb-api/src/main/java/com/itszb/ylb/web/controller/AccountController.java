package com.itszb.ylb.web.controller;

import com.itszb.ylb.beans.User;
import com.itszb.ylb.service.AccountService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.web.controller
 * @className: AccountController
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/23 10:29
 * @version: 1.0
 */
@RestController
@RequestMapping("account")
@CrossOrigin
public class AccountController {

    @Resource
    private AccountService accountService;
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("balance")
    public Double getBalance(@RequestHeader String token) {
        User user = (User) redisTemplate.opsForValue().get(token);
        return accountService.getBalance(user.getId());
    }
}
