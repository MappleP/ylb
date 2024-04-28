package com.itszb.ylb.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BidInfo implements Serializable {
    private Long id;
    private Long prodId;
    private Long uid;
    private Double bidMoney;
    private Date bidTime;
}
