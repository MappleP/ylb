package com.itszb.ylb.mapper;

import com.itszb.ylb.beans.Income;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Date;
import java.util.List;

public interface IncomeMapper {
    @Insert("insert into b_income_record values(#{id},#{uid},#{prodId},#{bidId},#{bidMoney},#{incomeDate},#{incomeMoney},#{incomeStatus})")
    void add(Income income);

    @Select("select * from b_income_record where income_status=0 and income_date <= #{today}")
    List<Income> getReturns(Date today);

    @Update("update b_income_record set income_status=1 where id=#{id}")
    void setStatus(Long id);
}
