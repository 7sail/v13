package com.qf.v13centerweb.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IProductService;
import com.qf.v13.entity.TProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IProductController {
    @Reference
    private IProductService productService;
    @ResponseBody
    @RequestMapping("get/{id}")
    public TProduct getByid(@PathVariable("id") Long id){
        return productService.selectByPrimaryKey(1L);
    }

}
