package com.itszb.ylb.web.config;

import com.itszb.ylb.web.interceptor.LoginInterceptor;
import com.itszb.ylb.web.interceptor.ResetLoginTimeout;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.web.config
 * @className: WebConfig
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/25 9:22
 * @version: 1.0
 */
@Configuration//配置类
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    @Resource
    private ResetLoginTimeout resetLoginTimeout;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        resetLoginTimeout(registry);
        login(registry);
    }

    private void login(InterceptorRegistry registry) {
        List<String> paths = new ArrayList<>();
        paths.add("/bid/bid");
        paths.add("/account/balance");
        registry.addInterceptor(loginInterceptor).addPathPatterns(paths);
    }
    private void resetLoginTimeout(InterceptorRegistry registry) {
        registry.addInterceptor(resetLoginTimeout).addPathPatterns("/**");
    }
}
