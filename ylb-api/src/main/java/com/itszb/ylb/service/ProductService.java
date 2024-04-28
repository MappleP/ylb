package com.itszb.ylb.service;

import com.itszb.ylb.beans.Product;
import okhttp3.internal.Internal;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
Double getAvgRate();

    // 首页--新手宝
    Product getNew();

    // 首页--优选产品
    List<Product> getYx();

    // 首页--散标产品
    List<Product> getSb();
    List<Product> getByType(Integer type);

    Product getById(Long id);
}
