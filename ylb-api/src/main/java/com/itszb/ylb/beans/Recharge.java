package com.itszb.ylb.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Recharge implements Serializable {
    private Long id;
    private Long uid;
    private String rechargeNo;
    private Integer rechargeStatus;
    private Double rechargeMoney;
    private Date rechargeTime;
    private String rechargeDesc;
    private String channel;
}
