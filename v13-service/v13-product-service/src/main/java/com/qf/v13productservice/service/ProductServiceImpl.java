package com.qf.v13productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.v13.api.IProductService;
import com.qf.v13.common.base.BaseServiceImpl;
import com.qf.v13.common.base.IBaseDao;
import com.qf.v13.entity.TProduct;
import com.qf.v13.entity.TProductDesc;
import com.qf.v13.entity.TProductType;
import com.qf.v13.mapper.TProductDescMapper;
import com.qf.v13.mapper.TProductMapper;
import com.qf.v13.mapper.TProductTypeMapper;
import com.qf.v13.pojo.TProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService{
    @Autowired
    private TProductMapper productMapper;
    @Autowired
    private TProductDescMapper productDescMapper;
    @Autowired
    private TProductTypeMapper productTypeMapper;
    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return productMapper;
    }

    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageIndex,pageSize);
        //2.获取数据
        List<TProduct> list = list();
        //3.构建一个分页对象
        PageInfo<TProduct> pageInfo = new PageInfo<TProduct>(list,2);
        return pageInfo;
    }

    @Override
    @Transactional
    public Long save(TProductVO vo) {
        TProduct product = vo.getProduct();
        product.setFlag(true);
        int count = productMapper.insert(product);
        TProductDesc tProductDesc = new TProductDesc();
        tProductDesc.setProductDesc(vo.getProductDesc());
        tProductDesc.setProductId(product.getId());
        productDescMapper.insert(tProductDesc);
        return product.getId();
    }

    @Override
    public int batchDel(List<Long> ids) {
        return productMapper.batchDel(ids);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        TProduct tProduct = new TProduct();
        tProduct.setId(id);
        tProduct.setFlag(false);
        return  productMapper.updateByPrimaryKeySelective(tProduct);
    }
}
