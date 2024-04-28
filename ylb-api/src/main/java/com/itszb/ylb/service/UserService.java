package com.itszb.ylb.service;

import com.itszb.ylb.beans.User;

import java.util.Date;

public interface UserService {
    Boolean checkExists(String phone);

    void addUser(String phone, String password, Date addTime);

    User login(String phone, String password);

    User login2(String phone);

    void auth(Long id, String name, String idCard);

    void updateHeaderImage(Long id, String headerImage);
}
