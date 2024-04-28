package com.itszb.ylb.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Product implements Serializable {
    private Long id;
    private String productName;
    private Double rate;
    private Integer cycle; // 周期：单位是月
    private Date releaseTime;
    private Integer productType; //产品类型 0新手宝，1优选产品，2散标产品
    private String productNo;
    private Double productMoney;
    private Double leftProductMoney;
    private Double bidMinLimit;
    private Double bidMaxLimit;
    private Integer productStatus;
    private Date productFullTime;
    private String productDesc;

}
