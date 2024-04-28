package com.itszb.ylb.service.impl;

import com.itszb.ylb.beans.User;
import com.itszb.ylb.exception.MsgTipException;
import com.itszb.ylb.mapper.AccountMapper;
import com.itszb.ylb.mapper.UserMapper;
import com.itszb.ylb.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.service.impl
 * @className: UserServiceIOmpl
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/15 11:04
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AccountMapper accountMapper;
    @Value("${salt}")
    private String salt;

    @Override
    public Boolean checkExists(String phone) {
        return userMapper.checkExists(phone);
    }

    @Override
    public void addUser(String phone, String password, Date addTime) {
        // 对密码进行MD5加密处理,得到一个固定长度为32位的密文
        //password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        /*
            通过比对数据库的方式，能够对普通的md5加密进行“破解”，这种方式称为“碰撞破解法”
            解决方案：
            在用户输入的密码之前加入一个字符串，使密码的复杂度增高，然后再进行加密处理，从而增加破解的难度
            这种方式叫做“加盐”，系统为用户增加的字符串就是“盐”
         */
        password = salt + password;
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (checkExists(phone)) {
            throw new MsgTipException("手机号已注册");
        }
        userMapper.addUser(phone, password, addTime);
        User user = getUserByPhone(phone);
        accountMapper.create(user.getId());

    }

    @Override
    public User login(String phone, String password) {
        password = salt + password;
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        User user = userMapper.login(phone, password);
        userMapper.setLastLoginTime(user.getId(), new Date());

        if (user == null) {
            throw new MsgTipException("账号或密码不正确！");
        }

        return user;
    }

    @Override
    public User login2(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public void auth(Long id, String name, String idCard) {
        userMapper.auth(id, name, idCard);
    }

    @Override
    public void updateHeaderImage(Long id, String headerImage) {
        userMapper.updateHeaderImage(id, headerImage);
    }
}
