package com.itszb.ylb.mapper;

import com.itszb.ylb.beans.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface UserMapper {
    @Select("select count(*) from u_user where phone=#{phone}")
    Boolean checkExists(String phone);

    @Insert("insert into u_user(phone,login_password,add_time) values(#{phone},#{password},#{addTime})")
    void addUser(String phone, String password, Date addTime);

    @Select("select * from u_user where phone=#{phone} and login_password=#{password}")
    User login(String phone, String password);

    @Select("select * from u_user where phone=#{phone}")
    User getUserByPhone(String phone);

    @Update("update u_user set name=#{name},id_card=#{idCard} where id=#{id} ")
    void auth(Long id, String name, String idCard);

    @Update("update u_user set last_login_time = #{date} where id = #{id}")
    void setLastLoginTime(Long id, Date date);

    @Update("update u_user set header_image=#{headerImage} where id=#{id}")
    void updateHeaderImage(Long id, String headerImage);
}
