package com.tracker.model;

// import com.tracker.dao.Dao;

public class Category{
    private int cid;
    private String cname;


    
    public Category(String category){
        cname = category;
        cid = -1;
    }

    public Category(int cid,String cname){
        this.cid = cid;
        this.cname = cname;
    }

    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }

    
}