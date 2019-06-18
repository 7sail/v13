package com.qf.v13itemweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IProductService;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("item")
public class ItemController {
    @Reference
    private IProductService productService;
    @Autowired
    private Configuration configuration;
    @RequestMapping("createHTMLById/{id}")
    @ResponseBody
    public ResultBean createHTMLById(@PathVariable Long id){
        try {
            Template template = configuration.getTemplate("item.ftl");
            TProduct product = productService.selectByPrimaryKey(id);
            Map<String, Object> data = new HashMap<>();
            data.put("product",product);
            String serverPath = ResourceUtils.getURL("classpath:static").getPath();
            String path = new StringBuilder(serverPath).append(File.separator).append(id).append(".html").toString();
            FileWriter writer = new FileWriter(path);
            template.process(data,writer);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultBean("404","模板文件读取失败");
        } catch (TemplateException e) {
            e.printStackTrace();
            return new ResultBean("404","静态文件生成失败");
        }
        return new ResultBean("200","静态页生成成功");
    }
}
