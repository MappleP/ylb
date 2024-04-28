package com.itszb.ylb.service.impl;

import com.itszb.ylb.beans.Product;
import com.itszb.ylb.mapper.ProductMapper;
import com.itszb.ylb.service.ProductService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.impl
 * @className: ProductServiceImpl
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/12 14:57
 * @version: 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Double getAvgRate() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Double rate = (Double) valueOperations.get("product:rate");
        if (rate == null) {
            rate = productMapper.getAvgRate();
            valueOperations.set("product:rate", rate, 2, TimeUnit.HOURS);

        }

        return rate;
    }

    @Override
    public Product getNew() {
        return productMapper.getNew();
    }

    @Override
    public List<Product> getYx() {
        return productMapper.getYx();
    }

    @Override
    public List<Product> getSb() {
        return productMapper.getSb();
    }

    @Override
    public List<Product> getByType(Integer type) {
        return productMapper.getByType(type);
    }

    @Override
    public Product getById(Long id) {
        return productMapper.getById(id);
    }
}
