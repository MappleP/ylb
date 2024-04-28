package com.itszb.ylb.mapper;

import com.itszb.ylb.beans.BidInfo;
import com.itszb.ylb.beans.RecordOfProd;
import com.itszb.ylb.beans.RecordOfUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

public interface BidInfoMapper {
    @Select("select phone,sum(bid_money) money from b_bid_info a left join u_user b on a.uid=b.id  GROUP BY uid ORDER BY money desc limit 3")
    List<Map> top3();

    @Select("select b.phone,a.bid_money,bid_time from b_bid_info a LEFT JOIN u_user b on a.uid=b.id where a.prod_id=#{id} ORDER BY bid_time desc limit 10")
    List<RecordOfProd> recordsOfProd(Long id);

    @Insert("insert into b_bid_info values(#{id},#{prodId},#{uid},#{bidMoney},#{bidTime})")
    void add(BidInfo bidInfo);

    @Select("select b.product_name,a.bid_money,a.bid_time from b_bid_info a " +
            " left join b_product_info b on a.prod_id=b.id where a.uid=#{id} " +
            " order by a.bid_time desc limit 6")
    List<BidInfo> getByProdId(Long pid);

    @Select("select b.product_name,a.bid_money,a.bid_time from b_bid_info a " +
            " left join b_product_info b on a.prod_id=b.id where a.uid=#{id} " +
            " order by a.bid_time desc limit 6")
    List<RecordOfUser> recordsOfUser(Long id);

}
