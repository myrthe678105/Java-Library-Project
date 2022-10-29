package com.example.javaendasssignment.controller;

import com.example.javaendasssignment.data.Database;
import com.example.javaendasssignment.model.Book;
import com.example.javaendasssignment.model.Status;
import com.example.javaendasssignment.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;


public class LendReceiveController implements Initializable {
    private final Database database;

    @FXML
    TextField lendItemCodetxt, memIDtxt, receiveItemCodetxt;

    @FXML
    Label lendErrortxt, receiveErrortxt;
    public static final Logger logger = Logger.getLogger(LendReceiveController.class.getName());

    private ObservableList<User> users;
    private ObservableList<Book> books;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        users = FXCollections.observableArrayList(database.getUsers());
        books = FXCollections.observableArrayList(database.getBooks());
    }


    public void onLendItemClick(ActionEvent event){
        receiveErrortxt.setText("");
        lendErrortxt.setText("");
        lendErrortxt.setTextFill(Color.RED);
        User member;
        Book item;
        if (lendItemCodetxt.getText().equals("") || memIDtxt.getText().equals("")){ //validate inputs
            lendErrortxt.setText("Please input Item code and Member code");
        } else {
            try{
                member = validateUser(parseInt(memIDtxt.getText()));
                item = validateBook(parseInt(lendItemCodetxt.getText()), lendErrortxt);
            } catch (NumberFormatException e){
                lendErrortxt.setText("use a valid code (only numbers)");
                logger.log(Level.WARNING, "Input not an integer");
                member = null;
                item = null;
            }
            if (item.getStatus() == Status.no) {
                lendErrortxt.setText("Book not available");
            }else if (member != null && item != null){
                item.setStatus(Status.no); //change status of book
                item.setLenderID(member.getIdentifier());
                item.setLendingDate(LocalDate.now()); //lending date change
                lendErrortxt.setTextFill(Color.GREEN);
                lendErrortxt.setText("Item Is Lent Successfully!!");//message saying item is lent
            }
        }
    }

    public void onReceiveItemClick(ActionEvent event){
        receiveErrortxt.setText("");
        lendErrortxt.setText("");
        receiveErrortxt.setTextFill(Color.RED);
        Book returningItem;
        if (receiveItemCodetxt.getText().equals("")){ //validate inputs
            receiveErrortxt.setText("Please input Item code");
        } else {
            try{
                returningItem = validateBook(parseInt(receiveItemCodetxt.getText()), receiveErrortxt);
            } catch (NumberFormatException e){
                receiveErrortxt.setText("use a valid code (only numbers)");
                logger.log(Level.WARNING, "Input not an integer");
                returningItem = null;
            }
            if (returningItem != null && returningItem.getStatus() == Status.no){
                Period period = Period.between(returningItem.getLendingDate(), LocalDate.now());
                if (period.getDays() > 21){ //compare lending date to now, if more than 3 weeks, display message that item too late (by how many days)
                    receiveErrortxt.setText("Item is late by " + (period.getDays()-21) + " days");
                } else{
                    receiveErrortxt.setTextFill(Color.GREEN);
                    receiveErrortxt.setText("Item Is returned Successfully!!");
                }
                returningItem.setStatus(Status.yes);//change status of book
                returningItem.setLenderID(null);
                returningItem.setLendingDate(null); //lending date cleared
            } else{
                receiveErrortxt.setText("Book is not lent out");
            }
        }
    }

    public Book validateBook (Integer itemCode, Label label){
        List<Book> collection = books;
        for (Book book : collection){
            if (itemCode.equals(book.getItemCode())){
                return book;
            }
        }
        label.setText("Could not find book with matching Item Code");
        return null;
    }
    public User validateUser (Integer identifier){
        ObservableList<User> members = users;
        for (User member : members){
            if (identifier.equals(member.getIdentifier())){
                return member;
            }
        }
        lendErrortxt.setText("Could not find member with matching Identifier");
        return null;
    }

    public LendReceiveController(Database database) {
        this.database = database;
    }
}
