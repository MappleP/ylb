package com.itszb.ylb.web.controller;

import com.itszb.ylb.beans.Product;
import com.itszb.ylb.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.web.controller
 * @className: ProductController
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/12 15:00
 * @version: 1.0
 */
@RestController // @Controller + @ResponseBody
@RequestMapping("product")
@CrossOrigin//允许跨域请求
public class ProductController {
    @Resource
    private ProductService productService;

    @GetMapping("list")
    public List index(Integer type) {
        return productService.getByType(type);
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("index")
    public Map index() {
        return new HashMap() {
            {
                put("new", productService.getNew());
                put("yxs", productService.getYx());
                put("sbs", productService.getSb());
            }
        };
    }

    @GetMapping("rate")
    public Double getRate() {
        return productService.getAvgRate();
    }
}
