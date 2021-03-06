package com.qf.v13.pojo;

import com.qf.v13.entity.TProduct;

import java.io.Serializable;

public class TProductVO implements Serializable {
    private TProduct product;
    private String productDesc;

    public TProductVO(TProduct product, String productDesc) {
        this.product = product;
        this.productDesc = productDesc;
    }

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
