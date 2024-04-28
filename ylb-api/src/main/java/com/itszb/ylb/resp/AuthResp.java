package com.itszb.ylb.resp;

import lombok.Data;

import java.util.Map;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.resp
 * @className: AuthResp
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/20 10:21
 * @version: 1.0
 */
 /*"code": "0", //返回码，0：成功，非0：失败（详见错误码定义）
         //当code=0时，再判断下面result中的res；当code!=0时，表示调用已失败，无需再继续
         "message": "成功", //返回码说明
         "result": {
         "name": "冯天", //姓名
         "idcard": "350301198011129422", //身份证号
         "res": "1", //核验结果状态码，1 一致；2 不一致；3 无记录
         "description": "一致",  //核验结果状态描述
         "sex": "男",
         "birthday": "19940320",
         "address": "江西省南昌市东湖区"
         }*/
@Data
public class AuthResp {
    private Integer code;
    private String message;
    private Map result;
}
