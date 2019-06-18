package com.qf.v13searchweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13search.api.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("search")
public class SearchController {
    @Reference
    private ISearchService searchService;
    @RequestMapping("queryByKeywords")
    public String queryByKeywords(String keyWords, Model model){
        ResultBean resultBean = searchService.queryByKeywords(keyWords);
        model.addAttribute("resultBean",resultBean);
        return "list";
    }
}
