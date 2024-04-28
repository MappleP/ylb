package com.itszb.ylb.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Income implements Serializable {
    private Long id;
    private Long uid;
    private Long prodId;
    private Long bidId;
    private Double bidMoney;
    private Date incomeDate;
    private Double incomeMoney;
    // 返现状态：0：未返现，1：已返现
    private Integer incomeStatus;
}
