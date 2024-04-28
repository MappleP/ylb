package com.itszb.ylb.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountMapper {
    @Select("select available_money from u_finance_account where uid = #{uid}")
    Double getBalance(Long uid);

    @Insert("insert into u_finance_account values (null,#{id},0)")
    void create(Long id);

    @Update("update u_finance_account set available_money = available_money-#{bidMoney}" +
            " where uid = #{uid} and available_money >= #{bidMoney}")
    int deductMoney(Long uid, Double bidMoney);
}
