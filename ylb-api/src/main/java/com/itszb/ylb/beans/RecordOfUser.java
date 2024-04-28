package com.itszb.ylb.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RecordOfUser implements Serializable {
    private String productName;
    private Double bidMoney;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bidTime;
}
