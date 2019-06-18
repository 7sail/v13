package com.qf.v13productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.api.IProductDescService;
import com.qf.v13.common.base.BaseServiceImpl;
import com.qf.v13.common.base.IBaseDao;
import com.qf.v13.entity.TProductDesc;
import com.qf.v13.mapper.TProductDescMapper;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ProductDescServiceImpl extends BaseServiceImpl<TProductDesc> implements IProductDescService {
    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao<TProductDesc> getBaseDao() {
        return productDescMapper;
    }

    @Override
    public TProductDesc selectByProductId(Long id) {
        return productDescMapper.selectByProductId(id);
    }
}
