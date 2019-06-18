package com.qf.v13centerweb.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.qf.v13.api.IProductDescService;
import com.qf.v13.api.IProductService;
import com.qf.v13.api.IProductTypeService;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;
import com.qf.v13.entity.TProductDesc;
import com.qf.v13.entity.TProductType;
import com.qf.v13.pojo.TProductVO;
import com.qf.v13search.api.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("product")
public class IProductController {
    @Reference
    private ISearchService searchService;
    @Reference
    private IProductService productService;
    @Reference
    private IProductTypeService productTypeService;
    @Reference
    private IProductDescService productDescService;
    @ResponseBody
    @RequestMapping("get/{id}")
    public TProduct getByid(@PathVariable("id") Long id){
        return productService.selectByPrimaryKey(1L);
    }

    @RequestMapping("list")
    public String list(Model model){
        //1.获取数据
        List<TProduct> list = productService.list();
        //2.保存数据到model
        model.addAttribute("list",list);
        //3.跳转到页面展示
        return "product/list";
    }

    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(@PathVariable("pageIndex") Integer pageIndex,
                       @PathVariable("pageSize") Integer pageSize,
                       Model model){
        PageInfo<TProduct> page = productService.page(pageIndex, pageSize);
        model.addAttribute("page",page);
        List<TProductType> typelist = productTypeService.list();
        model.addAttribute("typelist",typelist);
        return "product/list";
    }
    @PostMapping("getOption")
    @ResponseBody
    public ResultBean getOption(){
        List<TProductType> list = productTypeService.list();
        if (list!=null){
            return new ResultBean("200",list);
        }else{
            return new ResultBean("404","你懂的");
        }

    }
    @RequestMapping("add")
    public String add(TProductVO vo){
        Long id = productService.save(vo);
        searchService.synAllDataById(id);

        return "redirect:/product/page/1/3";
    }
    @PostMapping("delById/{id}")
    @ResponseBody
    public ResultBean delById(@PathVariable("id") Long id){
        int count = productService.deleteByPrimaryKey(id);
        if(count>0){
            return new ResultBean("200","删除成功");
        }else{
            return new ResultBean("404","删除失败，你懂得！");
        }

    }
    @PostMapping("toUpdate/{id}")
    @ResponseBody
    public ResultBean toUpdate(@PathVariable Long id){
        TProduct tProduct = productService.selectByPrimaryKey(id);
        TProductDesc tProductDesc = productDescService.selectByProductId(id);
        TProductVO tProductVO = new TProductVO(tProduct,tProductDesc.getProductDesc());
        if (tProductVO!=null){
            return new ResultBean("200",tProductVO);
        }else {
            return new ResultBean("404","你懂得");
        }

    }

    @PostMapping("update")
    public String update(TProductVO vo){
        Long count =productService.update(vo);
        return "redirect:/product/page/1/3";
    }
    @PostMapping("batchDel")
    @ResponseBody
    public ResultBean batchDel(@RequestParam List<Long> ids){
        int count= productService.batchDel(ids);
        if(count>0){
            return new ResultBean("200","删除成功");
        }else{
            return new ResultBean("404","删除失败，你懂得！");
        }
    }

}
