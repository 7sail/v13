package com.qf.v13search.api;

import com.qf.v13.common.pojo.PageResultBean;
import com.qf.v13.common.pojo.ResultBean;

public interface ISearchService {
    public ResultBean synAllData();
    public ResultBean synAllDataById(Long id);
    public ResultBean queryByKeywords(String keywords);
    public ResultBean page(String keywords,Integer indexPage,Integer pageSize);
}
