package com.itszb.ylb.mapper;

import com.itszb.ylb.beans.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface ProductMapper {
    @Select("select avg(rate) from b_product_info")
    Double getAvgRate();

    // 首页--新手宝
    @Select("select * from b_product_info where product_type=0")
    Product getNew();

    // 首页--优选产品
    @Select("select * from b_product_info where product_type=1 and product_status=0 and (cycle=1 or cycle=3 or cycle=6)")
    List<Product> getYx();

    // 首页--散标产品
    @Select("select * from b_product_info where product_type=2 ORDER BY rate desc limit 3")
    List<Product> getSb();

    @Select("select * from b_product_info where product_type=#{type}")
    List<Product> getByType(Integer type);

    @Select("select * from b_product_info where id=#{id}")
    Product getById(Long id);

    @Update("update b_product_info set left_product_money=left_product_money-#{bidMoney}" +
            " where id=#{pid} and left_product_money>=#{bidMoney}")
    int deductMoney(Long pid, Double bidMoney);
    @Update("update b_product_info set product_status=1,product_full_time=#{now} where id=#{pid}")
    void setFull(Long pid, Date now);
}
