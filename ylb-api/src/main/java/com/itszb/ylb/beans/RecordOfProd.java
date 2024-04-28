package com.itszb.ylb.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RecordOfProd implements Serializable {
    private String phone;
    private Double bidMoney;
    private Date bidTime;
}
