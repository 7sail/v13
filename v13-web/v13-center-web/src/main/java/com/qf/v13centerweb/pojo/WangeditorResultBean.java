package com.qf.v13centerweb.pojo;

public class WangeditorResultBean {
    public int errno;
    public String[] data;

    public WangeditorResultBean() {
    }

    public WangeditorResultBean(int errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
