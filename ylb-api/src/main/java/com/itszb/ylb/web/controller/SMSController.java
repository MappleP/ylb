package com.itszb.ylb.web.controller;

import com.itszb.ylb.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.web.controller
 * @className: SMSController
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/14 9:48
 * @version: 1.0
 */
@RestController//一定要注意，被折磨死了
@RequestMapping("sms")
@CrossOrigin
public class SMSController {
    @Resource
    private RedisTemplate redisTemplate;
    @GetMapping("{type}")//可以根据你写的改变
    public String register(@PathVariable String type, String phone) {
        String code="";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("code:"+type+":"+phone,code,5, TimeUnit.MINUTES);//写的不一样，保存的就不一样

       /* String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "b7042e1efe67438f98b943d8ae77a34d";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);

        querys.put("param", "**code**:"+code+",**minute**:5");

        System.out.println("==================================");

//smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html

        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();
        *//*bodys.put("content","code:5201314");
        bodys.put("template_id", "CFT_ptdie100");
        bodys.put("phone_number",phone);*//*


        try {
            *//**
             * 重要提示如下:
             * HttpUtils请从\r\n\t    \t* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java\r\n\t    \t* 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             *//*
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println(code);
        return code;
    }
}
