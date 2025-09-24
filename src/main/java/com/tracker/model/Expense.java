package com.tracker.model;

import java.time.LocalDateTime;

public class Expense {
    private int eid;
    private String description;
    private long amount;
    private int idc; //foreign key
    private LocalDateTime date;

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
    public int getIdc() {
        return idc;
    }
    public void setIdc(int idc) {
        this.idc = idc;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    } 
}
