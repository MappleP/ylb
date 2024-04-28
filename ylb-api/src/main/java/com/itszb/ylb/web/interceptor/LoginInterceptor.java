package com.itszb.ylb.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component // 交给Spring管理
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        // 跨域请求的“预检”操作
        if (method.equalsIgnoreCase("options")) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token) || redisTemplate.opsForValue().get(token) == null) {
            Map result = new HashMap();
            result.put("success", false);
            result.put("errMsg", "请先登录！");
            result.put("op", "reLogin");
            String json = objectMapper.writeValueAsString(result);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
            return false;
        }
        return true;
    }
}
