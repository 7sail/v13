package com.qf.v13searchservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.common.pojo.PageResultBean;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;
import com.qf.v13.mapper.TProductMapper;
import com.qf.v13search.api.ISearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {
    @Autowired
    private SolrClient solrClient;
    @Autowired
    private TProductMapper tProductMapper;
    @Override
    public ResultBean synAllData() {
        List<TProduct> list = tProductMapper.list();
        for (TProduct product : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id", product.getId());
            document.setField("product_name", product.getName());
            document.setField("product_price", product.getPrice());
            document.setField("product_images", product.getImages());
            document.setField("product_sale_point", product.getSalePoint());
            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("404","数据添加到索引库失败！");
            }
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据提交到索引库失败！");
        }
        return new ResultBean("200","数据同步成功！");
    }

    @Override
    public ResultBean synAllDataById(Long id) {
        TProduct product = tProductMapper.selectByPrimaryKey(id);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", product.getId());
        document.setField("product_name", product.getName());
        document.setField("product_price", product.getPrice());
        document.setField("product_images", product.getImages());
        document.setField("product_sale_point", product.getSalePoint());
        try {
            solrClient.add(document);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据添加到索引库失败");
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据提交到索引库失败");
        }
        return new ResultBean("200","数据同步成功");
    }

    @Override
    public ResultBean queryByKeywords(String keywords) {
        SolrQuery solrQuery = new SolrQuery();
        if (StringUtils.isAnyEmpty(keywords)){
            solrQuery.setQuery("*:*");
        }else{
            solrQuery.setQuery("product_keywords:"+keywords);
        }

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");


        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            SolrDocumentList results = queryResponse.getResults();
            ArrayList<Object> list = new ArrayList<>(results.size());

            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            for (SolrDocument result : results) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(result.getFieldValue("id").toString()));
//                product.setName((result.getFieldValue("product_name").toString()));
                product.setPrice(Long.parseLong(result.getFieldValue("product_price").toString()));
                product.setImages((result.getFieldValue("product_images").toString()));
                product.setSalePoint((result.getFieldValue("product_sale_point").toString()));
                Map<String, List<String>> listMap = highlighting.get(result.getFieldValue("id"));
                List<String> stringList = listMap.get("product_name");
                if (stringList==null||stringList.isEmpty()){
                    product.setName((result.getFieldValue("product_name").toString()));
                }else{
                    product.setName(stringList.get(0));
                }

                list.add(product);
            }
            return new ResultBean("200",list);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","查询失败");
        }

    }

    @Override
    public ResultBean page(String keywords,Integer indexPage,Integer pageSize) {
        PageResultBean resultBean = new PageResultBean<>();
        resultBean.setPageNum(1);
        resultBean.setPageSize(6);
        if(indexPage!=null && pageSize!=null){
            resultBean.setPageNum(indexPage);
            resultBean.setPageSize(pageSize);
        }

        resultBean.setTotal(getTotal());
        int count =getTotal()%resultBean.getPageSize()==0?getTotal()/resultBean.getPageSize():getTotal()/resultBean.getPageSize()+1;
        resultBean.setPages(count);


        SolrQuery solrQuery = new SolrQuery();
        if (StringUtils.isAnyEmpty(keywords)){
            solrQuery.setQuery("*:*");
        }else{
            solrQuery.setQuery("product_keywords:"+keywords);
        }

        solrQuery.setStart((resultBean.getPageNum()-1)*resultBean.getPageSize());
        solrQuery.setRows(resultBean.getPageSize());

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");


        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            SolrDocumentList results = queryResponse.getResults();
            ArrayList<Object> list = new ArrayList<>(results.size());

            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            for (SolrDocument result : results) {
                TProduct product = new TProduct();
                product.setId(Long.parseLong(result.getFieldValue("id").toString()));
//                product.setName((result.getFieldValue("product_name").toString()));
                product.setPrice(Long.parseLong(result.getFieldValue("product_price").toString()));
                product.setImages((result.getFieldValue("product_images").toString()));
                product.setSalePoint((result.getFieldValue("product_sale_point").toString()));
                Map<String, List<String>> listMap = highlighting.get(result.getFieldValue("id"));
                List<String> stringList = listMap.get("product_name");
                if (stringList==null||stringList.isEmpty()){
                    product.setName((result.getFieldValue("product_name").toString()));
                }else{
                    product.setName(stringList.get(0));
                }

                list.add(product);
            }
            resultBean.setList(list);
            return new ResultBean("200",resultBean);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","查询失败");
        }

    }

    private int getTotal(){
        SolrQuery query = new SolrQuery("*:*");
        QueryResponse response = null;
        try {
            response = solrClient.query(query);
            SolrDocumentList results = response.getResults();
            return results.size();
        } catch (SolrServerException |IOException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
