package com.qf.v13.api;

import com.qf.v13.common.base.IBaseService;
import com.qf.v13.entity.TProductDesc;


public interface IProductDescService extends IBaseService<TProductDesc> {
    public TProductDesc selectByProductId(Long id);
}
