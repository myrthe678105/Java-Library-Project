package com.Example.EndAssignment.Model;

import java.io.*;
import java.time.LocalDate;

public class Item implements Serializable {


    public Item(int itemCode,String availability, String title, String author) {
        this.itemCode=itemCode;
        this.availability=availability;
        this.title=title;
        this.author=author;
    }
 public Item(int itemCode,String availability, String title, String author, LocalDate lendingDate) {
        this.itemCode=itemCode;
        this.availability=availability;
        this.title=title;
        this.author=author;
        this.lendingDate=lendingDate;
        setExpectedReturnDate(lendingDate);
    }

    public Item(String title, String author) {
        this.title=title;
        this.author=author;
        //I assume that once an item is created it is by default available
        this.availability="Yes";
    }

    private int itemCode;

    public Item(String[] attributes) {
        //this is the ctor to read from the file
        this.itemCode= Integer.parseInt(attributes[0]);
        this.title=attributes[1];
        this.author=attributes[2];
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
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

    private LocalDate expectedReturnDate;

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate lendingDate) {
        this.expectedReturnDate = lendingDate.plusDays(21);
    }

    private String availability;
    private String title;
    private String author;

    private LocalDate lendingDate;

}
