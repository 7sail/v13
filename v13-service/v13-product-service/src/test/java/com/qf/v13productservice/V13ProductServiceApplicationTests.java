package com.qf.v13productservice;

import com.github.pagehelper.PageInfo;
import com.qf.v13.api.IProductService;
import com.qf.v13.api.IProductTypeService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.entity.TProductDesc;
import com.qf.v13.entity.TProductType;
import com.qf.v13.pojo.TProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13ProductServiceApplicationTests {

    @Autowired
    private IProductService productService;
    @Autowired
    private IProductTypeService productTypeService;
    @Test
    public void contextLoads() {
//        PageInfo<TProduct> page = productService.page(1, 2);
//        System.out.println(page.getList().size());
//        TProduct tProduct = new TProduct();
//        tProduct.setName("华为笔记本");
//        tProduct.setPrice(6000L);
//        tProduct.setSalePoint("巨丑");
//        tProduct.setTypeName("笔记本");
//        tProduct.setSalePrice(5988L);
//        tProduct.setImages("暂无");
//        tProduct.setTypeId(1L);
//        TProductVO tProductVO = new TProductVO();
//        tProductVO.setProduct(tProduct);
//        tProductVO.setProductDesc("又丑又贵");
//        productService.save(tProductVO);
        List<TProductType> list = productTypeService.list();
        System.out.println(list.size());
    }
}
