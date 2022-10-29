package com.example.javaendasssignment.controller;

import com.example.javaendasssignment.data.Database;
import com.example.javaendasssignment.model.Book;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.javaendasssignment.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CollectionDialogController implements Initializable{
    @FXML
    private TextField bookTitletxt, bookAuthortxt;
    @FXML
    private Button addBookbtn;
    @FXML
    Label dialogErrorLbl;

    Boolean edit;
    private ObservableList<Book> books;

    private final Database database;
    Book newBook;


    public CollectionDialogController(Database database) {

        this.database = database;
        edit = false;
    }
    public CollectionDialogController(Database database, Book book) {

        this.database = database;
        newBook = book;
        edit = true;
    }

    public Book getBook(){
        return newBook;
    }

    public ObservableList<Book> getCollection() {
        return FXCollections.observableList(books);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        books = FXCollections.observableArrayList(database.getBooks());
    }

    public void bookIsBeingEdited(Book book){
        edit = true;
        newBook = book;
        fillInText();
    }

    public void fillInText(){
        bookTitletxt.setText(newBook.getTitle());
        bookAuthortxt.setText(newBook.getAuthor());
        addBookbtn.setText("Edit Book");
    }

    public void onAddbtnClick(ActionEvent event){
        if (bookTitletxt.getText() == "" || bookAuthortxt.getText() == "") {
            dialogErrorLbl.setTextFill(Color.RED);
            dialogErrorLbl.setText("Please fill in Title and Author!");
        }else if(edit){ // only edits the title or author
            newBook.setAuthor(bookAuthortxt.getText());
            newBook.setTitle(bookTitletxt.getText());
            for (Book book: books){
                if (book.getItemCode().equals(newBook.getItemCode())){
                    book = newBook;
                }
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            newBook = new Book(getNewItemCode(), Status.yes, bookTitletxt.getText(), bookAuthortxt.getText());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void onCancelbtnClick(ActionEvent event){
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();

    }
    public int getNewItemCode() {
        return (books.get(books.size() - 1).getItemCode() + 1);
    }
}
