package com.itszb.ylb.web.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class ResetLoginTimeout implements HandlerInterceptor {
    @Resource
    private RedisTemplate redisTemplate;

    @Value("${timeout}")
    private int timeout;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
            redisTemplate.expire(token, timeout, TimeUnit.MINUTES);
        }
        return true;
    }
}
