package com.qf.v13.api;

import com.github.pagehelper.PageInfo;
import com.qf.v13.common.base.IBaseService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.TProductVO;

import java.util.List;

public interface IProductService extends IBaseService<TProduct> {
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);
    public Long save(TProductVO vo);

    int batchDel(List<Long> ids);

    Long update(TProductVO vo);
}
