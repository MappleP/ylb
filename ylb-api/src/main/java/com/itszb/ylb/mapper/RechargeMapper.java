package com.itszb.ylb.mapper;

import com.itszb.ylb.beans.Recharge;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface RechargeMapper {
    @Insert("insert into b_recharge_record values(" +
            "#{id}," +
            "#{uid}," +
            "#{rechargeNo}," +
            "#{rechargeStatus}," +
            "#{rechargeMoney}," +
            "#{rechargeTime}," +
            "#{rechargeDesc}," +
            "#{channel}" +
            ")")
    int add(Recharge recharge);

    @Update("update b_recharge_record set recharge_status=#{status},channel=#{channel} where recharge_no=#{rechargeNo}")
    void update(String rechargeNo, Integer status, String channel);
}
