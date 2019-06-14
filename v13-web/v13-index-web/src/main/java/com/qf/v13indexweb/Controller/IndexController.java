package com.qf.v13indexweb.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IProductTypeService;
import com.qf.v13.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController {
    @Reference
    IProductTypeService productTypeService;
    @RequestMapping("home")
    public String showHome(Model model){
        List<TProductType> list = productTypeService.list();
        model.addAttribute("list",list);
        return "home";
    }

}
