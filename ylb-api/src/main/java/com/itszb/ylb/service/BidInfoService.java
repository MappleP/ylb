package com.itszb.ylb.service;

import com.itszb.ylb.beans.Product;
import com.itszb.ylb.beans.RecordOfProd;
import com.itszb.ylb.beans.RecordOfUser;

import java.util.List;
import java.util.Map;

public interface BidInfoService {
    List<Map> top3();

    List<RecordOfProd> recordsOfProd(Long id);

    void bid(Double bidMoney, Long pid, Long uid);

    List<RecordOfUser> recordsOfUser(Long id);
}
