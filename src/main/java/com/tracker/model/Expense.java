package com.tracker.model;

import java.time.LocalDateTime;

public class Expense {
    private int eid;
    private String description;
    private long amount;
    private String cname; //foreign key
    private LocalDateTime date;

    // constructor for adding a new expense
    public Expense(int eid,String description, int amount,String cname,LocalDateTime date){
        this.eid = eid;
        this.description = description;
        this.amount = amount;
        this.cname = cname;
        this.date = date;
    }

    public int getEid() {
        return eid;
    }
    public void setEid(int eid) {
        this.eid = eid;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }
    public String getCname() {
        return cname;
    }
    public void setIdc(String cname) {
        this.cname = cname;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    } 
}
