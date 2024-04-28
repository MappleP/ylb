package com.itszb.ylb.web.controller;

import com.itszb.ylb.beans.User;
import com.itszb.ylb.service.BidInfoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.PipedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.web.controller
 * @className: BidInfoController
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/22 14:30
 * @version: 1.0
 */
@RestController
@RequestMapping("bid")
@CrossOrigin
public class BidInfoController {
    @Resource
    private BidInfoService bidInfoService;
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("records2")
    public List records2(@RequestHeader String token) {
        User user = (User) redisTemplate.opsForValue().get(token);
        return bidInfoService.recordsOfUser(user.getId());
    }

    @GetMapping("top3")
    public List top3() {
        return bidInfoService.top3();
    }

    @GetMapping("records")
    public List records(Long id) {
        return bidInfoService.recordsOfProd(id);
    }

    @PostMapping("bid")
    public Map bid(Double bidMoney, Long pid, @RequestHeader String token) {
        //首先查看是否登录
        User user = (User) redisTemplate.opsForValue().get(token);
        //获取登录用户的ID
        Long uid = user.getId();
        //进行投资
        bidInfoService.bid(bidMoney, pid, uid);

        return new HashMap() {{
            put("success", true);
            put("msg", "投资成功！");
        }};
    }
}
