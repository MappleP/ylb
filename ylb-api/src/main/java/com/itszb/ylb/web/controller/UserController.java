package com.itszb.ylb.web.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itszb.ylb.beans.User;
import com.itszb.ylb.exception.MsgTipException;
import com.itszb.ylb.resp.AuthResp;
import com.itszb.ylb.service.UserService;
import com.itszb.ylb.utils.HttpUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.web.controller
 * @className: UserController
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/15 11:06
 * @version: 1.0
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Value("${timeout}")
    private int timeout;



    @PostMapping("upload")
    public String upload(MultipartFile file, @RequestHeader String token) throws Exception {
        //String filename = file.getOriginalFilename();
        User user = (User) redisTemplate.opsForValue().get(token);
        String filename = user.getId() + "";

        // 目标：/0~99/0~99/0~99/...
        String paths = "";
        String headerImage = user.getHeaderImage();
        if (StringUtils.isNotBlank(headerImage)) {
            // http://127.0.0.1/user/header     /10/50/35/68/90/0/81/5/3/19    /3
            int index = headerImage.indexOf("/user/header");
            paths = headerImage.substring(index + 12, headerImage.lastIndexOf("/"));
        } else {
            // 防止一个文件夹中存储过多文件，使用“目录分离”方式存储
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                paths += "/" + random.nextInt(100);
            }
        }

        File savePath = new File("d:/headerImage" + paths);
        savePath.mkdirs(); // 如果不存在会自动创建

        File filePath = new File(savePath, filename);
        file.transferTo(filePath);

        headerImage = "http://127.0.0.1/user/header" + paths + "/" + filename;
        // 同步数据库
        userService.updateHeaderImage(user.getId(), headerImage);
        // 更新缓存
        user.setHeaderImage(headerImage);
        redisTemplate.opsForValue().set(token, user, timeout, TimeUnit.MINUTES);
        // 返回图片路径，页面立即更新
        return headerImage;
    }
    // "http://127.0.0.1/user/header/1/66/44/22/xx.jpg"
    @GetMapping("header/**")
    public void showImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // uri: /user/header/1/66/44/22/xx.jpg
        String uri = request.getRequestURI();
        // filename: 1/66/44/22/xx.jpg
        String filename = uri.substring(13);
        String fullPath = "d:/headerImage/" + filename;
        InputStream in = new FileInputStream(fullPath);
        OutputStream out = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        out.close();
        in.close();
    }

    @GetMapping("checkExists")
    public Boolean checkExists(String phone) {
        return userService.checkExists(phone);
    }

    @PostMapping("auth")
    public Map auth(String name, String idCard, @RequestHeader String token) throws Exception {
        String url = "https://eid.shumaidata.com/eid/check";
        String appCode = "b7042e1efe67438f98b943d8ae77a34d";
        Map<String, String> params = new HashMap<>();
        params.put("idcard", idCard);
        params.put("name", name);
        System.out.println(params);
        String result = postForm(appCode, url, params);
        System.out.println(result);
        AuthResp authResp = objectMapper.readValue(result, AuthResp.class);
        if (authResp.getCode() == 0) {
            User user = (User) redisTemplate.opsForValue().get(token);
            Long id = user.getId();
            userService.auth(id, name, idCard);
            return new HashMap() {
                {
                    put("success", true);
                }
            };
        } else {
            throw new MsgTipException("姓名和身份证不匹配");
        }


    }

    /**
     * 用到的HTTP工具包：okhttp 3.13.1
     * <dependency>
     * <groupId>com.squareup.okhttp3</groupId>
     * <artifactId>okhttp</artifactId>
     * <version>3.13.1</version>
     * </dependency>
     */
    public static String postForm(String appCode, String url, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody.Builder formbuilder = new FormBody.Builder();
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            formbuilder.add(key, params.get(key));
        }
        FormBody body = formbuilder.build();
        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(body).build();
        Response response = client.newCall(request).execute();
        System.out.println("返回状态码" + response.code() + ",message:" + response.message());
        String result = response.body().string();
        return result;
    }

    @PostMapping("login2")
    public Map login2(String phone, String code) {
        String realCode = (String) redisTemplate.opsForValue().get("code:login:" + phone);
        if (realCode == null || !realCode.equals(code)) {
            throw new MsgTipException("验证码不正确！");
        }
        Map map = new HashMap();
        User user = userService.login2(phone);

        Boolean auth = !StringUtils.isBlank(user.getName());
        map.put("success", true);
        map.put("auth", auth);


        return map;

    }

    @GetMapping("isLogin")
    public Boolean isLogin(@RequestHeader String token) {
        User user = (User) redisTemplate.opsForValue().get(token);
        return user != null;
    }

    @PostMapping("login")
    public Map login(String phone, String password, HttpSession session) {
        Map map = new HashMap();
        User user = userService.login(phone, password);
        Date time= new Date();
        // 生成令牌（客户端标识）
        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        redisTemplate.opsForValue().set(token, user, timeout, TimeUnit.MINUTES);//失效时间
        if (user == null) {
            throw new MsgTipException("账号或者密码错误！");
        } else {
            Boolean auth = !StringUtils.isBlank(user.getName());
            map.put("success", true);
            map.put("auth", auth);
            map.put("token", token);
        }

        return map;
    }

    @PostMapping("register")
    public Map registerUser(String phone, String password, String code) {
        // 数据格式验证
        checkData(phone, password, code);
        Date addTime = new Date();

        userService.addUser(phone, password, addTime);

        Map map = new HashMap();
        map.put("success", true);
        return map;
    }

    private void checkData(String phone, String password, String code) {
        String realCode = (String) redisTemplate.opsForValue().get("code:register:" + phone);
        if (code == null || !code.matches("^\\d{4,6}$") || !code.equals(realCode)) {
            throw new MsgTipException("验证码不正确！");
        }
        if (phone == null || !phone.matches("^1[3-9]\\d{9}$")) {
            throw new MsgTipException("手机格式不正确！");
        }
        if (password == null || !password.matches("^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z]{6,20}$")) {
            throw new MsgTipException("密码必须是6-20位的字母和数字组合！");
        }

    }

    @GetMapping("info")
    private User info(@RequestHeader String token) {
        return (User) redisTemplate.opsForValue().get(token);

    }

}
