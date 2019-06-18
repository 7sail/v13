package com.qf.v13searchservice;

import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;
import com.qf.v13.mapper.TProductMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13SearchServiceApplicationTests {
    @Autowired
    private SolrClient solrClient;
    @Autowired
    private TProductMapper tProductMapper;
    @Test
    public void contextLoads() throws IOException, SolrServerException {
        List<TProduct> list = tProductMapper.list();
        for (TProduct product : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id", product.getId());
            document.setField("product_name", product.getName());
            document.setField("product_price", product.getPrice());
            document.setField("product_images", product.getImages());
            document.setField("product_sale_point", product.getSalePoint());
            solrClient.add(document);
        }
            solrClient.commit();
    }
    @Test
    public void queryByKeywords() throws IOException, SolrServerException {
        SolrQuery entries = new SolrQuery();
        entries.setQuery("*:*");
        QueryResponse query = solrClient.query(entries);
        SolrDocumentList results = query.getResults();
        for (SolrDocument result : results) {
            System.out.println(result.getFieldValue("id"));
        }
    }

}
