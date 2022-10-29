package com.example.javaendasssignment.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Book implements Serializable {
    private Integer itemCode;
    private Status status;
    private String title;
    private String author;
    private LocalDate lendingDate;
    private Integer LenderID;

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public Integer getLenderID() {
        return LenderID;
    }

    public void setLenderID(Integer lenderID) {
        LenderID = lenderID;
    }

    public Book(Integer itemCode, Status status, String title, String author) {
        this.itemCode = itemCode;
        this.title = title;
        this.author = author;
        this.status = status;
        this.LenderID = null;
    }
}
